/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import enums.PacketType
import helpers.Logger

class Server {

    var port: Int
    var ip: String
    private val registeredUserIDs: MutableList<Int> = mutableListOf<Int>()

    constructor(ip: String, port: Int) {
        this.ip = ip;
        this.port = port;
    }

    fun receivePacket(packet: Packet) {
        when (packet.type) {
            PacketType.REGISTRATION -> {
                Logger.info("Received registration event from -> ${packet.payload}")
                this.registeredUserIDs.add(packet.payload as Int)
            }
        }
    }

    override fun toString(): String {
        return "Server"
    }


}