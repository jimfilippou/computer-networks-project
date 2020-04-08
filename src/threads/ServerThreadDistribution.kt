/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package threads

import enums.PacketType
import factories.PacketFactory
import helpers.Logger
import models.ListUsersPacket
import models.RegistrationPacket
import models.Server
import models.UploadImagePacket
import java.io.File
import java.io.ObjectOutputStream

/**
 * Slave thread, for each request
 *
 * @param server the server object to update information based on type of packet & permissions
 * @param packet the incoming packet of type Packet
 * @param replyTo the output stream to write results to
 * @since 0.0.3
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
                Logger.debug("Received registration packet from -> ${packet.payload}")
                synchronized(this.server.counter) {
                    this.server.counter++
                    this.server.registeredUserIDs.add(this.server.counter)
                    Logger.debug("Creating directory \"c${this.server.counter}\" for user")
                    this.createUserDirectory(this.server.counter)
                    Logger.debug("Writing ${this.server.counter} to socket: $replyTo")
                    replyTo.writeObject(this.server.counter)
                }
                // TODO: Investigate the closure of the stream
                // too afraid to uncomment the following lines
                // Logger.debug("Closing stream...")
                // replyTo.close()
            }
            is UploadImagePacket -> {
                packet.payload = packet.payload as UploadImagePacket.UploadImagePayload
                Logger.debug(
                    "Received image upload event from -> ${(packet.payload as UploadImagePacket.UploadImagePayload).sender}\n " +
                            "Uploaded: ${(packet.payload as UploadImagePacket.UploadImagePayload).image}"
                )
                //  ¯\_(ツ)_/¯
            }
            is ListUsersPacket -> {
                Logger.debug("Received user list event from -> ${(packet.payload as ListUsersPacket.ListUsersPayload).sender}")
                val packet = factory.makePacket(PacketType.LIST_USER_IDS)
                packet.payload = ListUsersPacket.ListUsersPayload(server, server.registeredUserIDs)
                replyTo.writeObject(packet)
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