/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import helpers.Logger
import java.io.ObjectOutputStream

class Server(var ip: String, var port: Int) {

    private val registeredUserIDs: MutableList<Int> = mutableListOf<Int>()

    fun receivePacket(packet: Any, replyTo: ObjectOutputStream) {
        // TODO: Server must generate IDs for users, as well as passwords
        when (packet) {
            is RegistrationPacket -> {
                Logger.info("Received registration packet from -> ${packet.payload}")
                this.registeredUserIDs.add(packet.payload as Int)
            }
        }
    }

    override fun toString(): String {
        return "Server"
    }


}