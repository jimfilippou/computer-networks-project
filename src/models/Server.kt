/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import helpers.Logger
import interfaces.Packet

class Server {

    var port: Int
    var ip: String
    private val registeredUserIDs: MutableList<Int> = mutableListOf<Int>()

    constructor(ip: String, port: Int) {
        this.ip = ip;
        this.port = port;
    }

    fun receivePacket(packet: Any) {
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