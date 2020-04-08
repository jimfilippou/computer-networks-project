/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package threads

import helpers.Logger
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
 * @since 0.0.2
 */
class ServerThreadDistribution(
    private val server: Server,
    private val packet: Any,
    private val replyTo: ObjectOutputStream
) : Thread() {

    override fun run() {
        synchronized(this.server.slaves) {
            this.server.slaves += 1
        }
        when (packet) {
            is RegistrationPacket -> {
                Logger.debug("Received registration packet from -> ${packet.payload}")
                synchronized(this.server.counter) { this.server.counter++ }
                this.server.registeredUserIDs.add(this.server.counter.toInt())
                Logger.debug("Creating directory \"c${packet.payload}\" for user")
                this.createUserDirectory(this.server.counter)
                Logger.debug("Writing ${this.server.counter} to socket: $replyTo")
                replyTo.writeObject(this.server.counter)
                // Logger.debug("Closing stream...")
                // replyTo.close()
            }
            is UploadImagePacket -> {
                Logger.debug("Received image upload event from -> ${packet.payload?.client}\n Uploaded: ${packet.payload?.image}")
                //  ¯\_(ツ)_/¯
            }
        }
        synchronized(this.server.slaves) {
            this.server.slaves -= 1
        }
    }

    private fun createUserDirectory(uid: Int) {
        File("storage/c$uid").mkdir()
    }

}