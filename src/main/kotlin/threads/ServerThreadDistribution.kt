/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: ServerThreadDistribution.kt
 */

package threads

import aliases.*
import enums.PacketType
import enums.RequestStatus
import factories.PacketFactory
import helpers.Logger
import models.*
import models.packets.*
import java.io.File
import java.io.ObjectOutputStream
import java.lang.Exception

/**
 * A slave thread, to handle each assigned request, assigned by the master thread
 *
 * This class, extends Java's *Thread* class and overrides the *run* function for custom functionality.
 * It works in a synchronized way to avoid issues caused by multithreading, using the synchronized keyword.
 * By default, this thread will not run when you initiate the class, you need to explicitly call "run()" in order
 * to start the thread.
 *
 * @property server the server object to update information based on type of packet & permissions
 * @property packet the incoming packet of type Packet
 * @property replyTo the output stream to write results to
 * @constructor Creates a new slave thread which will handle the request that the master thread assigned to it
 * @since 2.0.0
 */
class ServerThreadDistribution(
        private val server: Server,
        private val packet: Any,
        private val replyTo: ObjectOutputStream,
        private val callback: () -> Unit
) : Thread() {

    private val factory: PacketFactory = PacketFactory()

    override fun run() {
        synchronized(this.server.slaves) {
            this.server.slaves += 1
        }
        when (packet) {
            is RegistrationPacket -> {
                Logger.debug("Received registration packet from -> ${(packet.payload as rp).sender}")
                synchronized(this.server.counter) {
                    // Update server counter
                    this.server.counter++
                    // Update incoming packet
                    ((packet.payload as rp).sender as Client).id = this.server.counter
                    // Update server hashmap & graph file
                    val sender = (packet.payload as rp).sender as Client
                    this.server.registeredUsers[this.server.counter] = sender
                    this.server.insertUserToGraphWithLock(sender)
                    Logger.debug("Creating directory \"c${this.server.counter}\" for user.")
                    this.createUserDirectory(this.server.counter)
                    val toSend = factory.makePacket(PacketType.REGISTRATION)
                    toSend.payload = RegistrationPacket.RegistrationPayload(server, this.server.counter)
                    Logger.debug("Creating empty list of posts.")
                    this.server.posts[this.server.counter] = mutableListOf()
                    Logger.debug("Writing $toSend to socket: $replyTo.")
                    replyTo.writeObject(toSend)
                }
                // TODO: Investigate the closure of the stream
                // too afraid to uncomment the following lines
                // Logger.debug("Closing stream...")
                // replyTo.close()
            }
            is ListUsersPacket -> {
                Logger.debug("Received user list event from -> ${(packet.payload as ListUsersPacket.ListUsersPayload).sender}")
                val toSend = factory.makePacket(PacketType.LIST_USER_IDS)
                val arr: MutableList<Int> = mutableListOf()
                for ((_, value) in server.registeredUsers) {
                    arr.add(value.id)
                }
                toSend.payload = ListUsersPacket.ListUsersPayload(server, arr)
                replyTo.writeObject(toSend)
            }
            is FollowUserPacket -> {
                Logger.debug("Follow requested, ${(packet.payload as fup).sender} wants to follow user with ID: ${(packet.payload as fup).uid}")
                val toSend = factory.makePacket(PacketType.FOLLOW_USER)
                toSend.payload = fup(server, success = true)
                if (((packet.payload as fup).sender as Client).id == (packet.payload as fup).uid) {
                    Logger.warn("You can't follow yourself :P")
                    (toSend.payload as fup).success = false
                } else {
                    val sender: Client? = this.server.registeredUsers[((packet.payload as fup).sender as Client).id]
                    val toBeFollowed: Int? = (packet.payload as fup).uid
                    if (sender == null) {
                        Logger.warn("Sender is null, this is not supposed to happen!")
                        return
                    }
                    if (toBeFollowed != null) {
                        val req = FollowRequest(sender, RequestStatus.PENDING)
                        val toFollow: Client = (this.server.registeredUsers[toBeFollowed] as Client)
                        toFollow.followRequests.add(req)
                        replyTo.writeObject("[Server] You requested to follow $toFollow, hopefully he/she will accept :)\n")
                    }
                }
                replyTo.writeObject(toSend)
            }
            is GetFollowRequestsPacket -> {
                Logger.debug("User ${(packet.payload as gfrp).sender} requested to see his/her followers.")
                val user = this.server.registeredUsers[(((packet.payload as gfrp).sender) as Client).id] as Client
                val toSend = factory.makePacket(PacketType.GET_FOLLOW_REQUESTS)
                toSend.payload = gfrp(this.server, user.followRequests)
                replyTo.writeObject(toSend)
            }
            is RejectFollowRequestPacket -> {
                val response = factory.makePacket(PacketType.REJECT_FOLLOW_REQUEST)
                response.payload = rfrp(this.server, null, true)
                try {
                    val sender = (packet.payload as rfrp).sender
                    Logger.debug("User $sender wants to decline request with ID: ${((packet.payload as rfrp).requestID)}")
                    val user: Client? = this.server.registeredUsers[(((packet.payload as rfrp).sender) as Client).id]
                    val found = user?.followRequests?.find { it.id == (packet.payload as rfrp).requestID }
                    found?.status = RequestStatus.REJECTED
                } catch (err: Exception) {
                    ((response.payload) as rfrp).success = false
                }
                replyTo.writeObject(response)
            }
            is AcceptFollowRequestPacket -> {
                val response = factory.makePacket(PacketType.ACCEPT_FOLLOW_REQUEST)
                response.response = true
                val sender = (packet.payload as afrp).sender
                Logger.debug("User $sender wants to accept request with ID: ${((packet.payload as afrp).requestID)}")
                val user: Client? = this.server.registeredUsers[(((packet.payload as afrp).sender) as Client).id]
                val found = user?.followRequests?.find { it.id == (packet.payload as afrp).requestID }
                found?.status = RequestStatus.ACCEPTED
                if (found != null) {
                    this.server.acceptedRequest(sender, found)
                }
                replyTo.writeObject(response)
            }
            is UploadPostPacket -> {
                Logger.debug(
                        "Received upload event from -> ${(packet.payload as uip).sender}\n " +
                                "Uploaded: ${(packet.payload as uip).post.image}"
                )
                this.server.posts[(packet.payload as uip).sender.id]?.add((packet.payload as uip).post)
                replyTo.writeChars("Uploaded post!")
            }
            is ShowPostOfXPacket -> {
                val sender = (packet.payload as x).sender
                val uid = (packet.payload as x).id
                Logger.debug("Received \"show post of X event\" from -> $sender X=$uid")
                val resp = factory.makePacket(PacketType.SHOW_POST_OF_X)
                if (uid == sender.id) {
                    // Show my posts (no checks)
                    resp.response = this.server.posts[sender.id]
                } else {
                    // todo: check permissions
                    resp.response = this.server.posts[uid]
                }
                replyTo.writeObject(resp)
            }
        }
        synchronized(this.server.slaves) {
            this.server.slaves -= 1
        }
        callback.invoke()
    }

    /**
     * Creates a user directory with a prefix of c-x where x is the ID of the user
     *
     * @param uid the user ID is used to create a folder with a proper name
     */
    private fun createUserDirectory(uid: Int) {
        File("storage/c$uid").mkdir()
    }

}