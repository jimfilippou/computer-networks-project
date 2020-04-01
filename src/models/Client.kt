/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import com.sun.xml.internal.ws.developer.Serialization
import enums.PacketType
import factories.PacketFactory
import helpers.sendToServer

class Client {

    var id: Int? = null;
    private val _followerIDs: MutableList<Int> = mutableListOf<Int>();

    fun addFollower(followerID: Int) {
        this._followerIDs.add(followerID);
    }

    fun register(where: Server) {
        val factory = PacketFactory()
        val packet: Packet = factory.makePacket(PacketType.REGISTRATION)
        packet.payload = this
        sendToServer(packet, this, where)
    }

}