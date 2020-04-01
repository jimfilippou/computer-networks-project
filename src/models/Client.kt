/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import enums.PacketType
import factories.PacketFactory
import helpers.sendToServer

class Client(id: Int = -1) {

    var id: Int = id
    private val _followerIDs: MutableList<Int> = mutableListOf<Int>();

    fun addFollower(followerID: Int) {
        this._followerIDs.add(followerID);
    }

    fun register(where: Server) {
        val factory = PacketFactory()
        val packet: RegistrationPacket = factory.makePacket(PacketType.REGISTRATION) as RegistrationPacket
        packet.payload = this.id
        sendToServer(packet, this, where)
    }

}