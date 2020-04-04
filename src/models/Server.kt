/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import helpers.Logger
import java.io.ObjectOutputStream

class Server(var ip: String, var port: Int) {

    private var counter = 0
    private val registeredUserIDs: MutableList<Int> = mutableListOf<Int>()

    fun receivePacket(packet: Any, replyTo: ObjectOutputStream) {
        when (packet) {
            is RegistrationPacket -> {
                Logger.info("Received registration packet from -> ${packet.payload}")
                counter++
                this.registeredUserIDs.add(counter)
                Logger.debug("Writing $counter to $replyTo")
                replyTo.writeObject(counter)
                // Logger.debug("Closing stream...")
                // replyTo.close()
            }
        }
    }

    override fun toString(): String {
        return "Server"
    }


}