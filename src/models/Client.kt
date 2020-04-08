/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import enums.PacketType
import factories.PacketFactory
import helpers.sendToServer
import java.io.Serializable

class Client(var id: Int = -1) : Serializable {

    private var loggedIn: Boolean = false
    private val followerIDs: MutableList<Int> = mutableListOf<Int>()
    private val factory: PacketFactory = PacketFactory()

    fun addFollower(followerID: Int) {
        this.followerIDs.add(followerID)
    }

    fun register(destination: Server) {
        val packet: RegistrationPacket = this.factory.makePacket(PacketType.REGISTRATION) as RegistrationPacket
        packet.payload = this.id
        sendToServer(packet, this, destination)
        this.loggedIn = true
    }

    fun dispatchUploadEvent(image: String, destination: Server) {
        val packet: UploadImagePacket = this.factory.makePacket(PacketType.UPLOAD_IMAGE) as UploadImagePacket
        packet.payload = UploadImagePacket.ImagePayload(this, image)
        sendToServer(packet, this, destination)
    }

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

}