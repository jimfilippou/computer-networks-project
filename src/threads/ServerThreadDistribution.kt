/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package threads

import helpers.Logger
import models.RegistrationPacket
import models.Server
import java.io.File
import java.io.ObjectOutputStream

/**
 * Slave thread, for each request
 */
class ServerThreadDistribution(
    private val server: Server,
    private val packet: Any,
    private val replyTo: ObjectOutputStream
) : Thread() {

    override fun run() {
        synchronized(this.server.slaves) {
            this.server.slaves += 1;
        }
        when (packet) {
            is RegistrationPacket -> {
                Logger.info("Received registration packet from -> ${packet.payload}")
                this.server.counter++
                this.server.registeredUserIDs.add(this.server.counter.toInt())
                Logger.debug("Creating directory \"c${packet.payload}\" for user")
                this.createUserDirectory(this.server.counter)
                Logger.debug("Writing ${this.server.counter} to socket: $replyTo")
                replyTo.writeObject(this.server.counter)
                // Logger.debug("Closing stream...")
                // replyTo.close()
            }
        }
        synchronized(this.server.slaves) {
            this.server.slaves -= 1;
        }
    }

    private fun createUserDirectory(uid: Int) {
        File("storage/c$uid").mkdir()
    }

}