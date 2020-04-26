/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: ServerThreadDistribution.kt
 */

package threads

import aliases.fup
import aliases.rp
import aliases.uip
import enums.PacketType
import enums.RequestStatus
import factories.PacketFactory
import helpers.Logger
import models.*
import models.packets.*
import java.io.File
import java.io.ObjectOutputStream

/**
 * A slave thread, to handle each assigned request
 *
 * This class, extends Java's *Thread* class and overrides the *run* function for custom functionality.
 * It works in a synchronized way to avoid issues caused by multithreading.
 *
 * @property server the server object to update information based on type of packet & permissions
 * @property packet the incoming packet of type Packet
 * @property replyTo the output stream to write results to
 * @constructor Creates a new slave thread which will handle the request that the master thread assigned to it
 * @since 1.0.1
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
                    Logger.debug("Creating directory \"c${this.server.counter}\" for user")
                    this.createUserDirectory(this.server.counter)
                    val toSend = factory.makePacket(PacketType.REGISTRATION)
                    toSend.payload = RegistrationPacket.RegistrationPayload(server, this.server.counter)
                    Logger.debug("Writing $toSend to socket: $replyTo")
                    replyTo.writeObject(toSend)
                }
                // TODO: Investigate the closure of the stream
                // too afraid to uncomment the following lines
                // Logger.debug("Closing stream...")
                // replyTo.close()
            }
            is UploadImagePacket -> {
                Logger.debug(
                    "Received image upload event from -> ${(packet.payload as uip).sender}\n " +
                            "Uploaded: ${(packet.payload as uip).image}"
                )
                //  ¯\_(ツ)_/¯
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
                toSend.payload = FollowUserPacket.FollowUserPayload(server, success = true)
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
        }
        synchronized(this.server.slaves) {
            this.server.slaves -= 1
        }
        callback.invoke()
    }

    private fun createUserDirectory(uid: Int) {
        File("storage/c$uid").mkdir()
    }

}