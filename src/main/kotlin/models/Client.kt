/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: Client.kt
 */

package models

import enums.PacketType
import factories.PacketFactory
import helpers.sendToServer
import models.packets.FollowUserPacket
import models.packets.ListUsersPacket
import models.packets.RegistrationPacket
import models.packets.UploadImagePacket
import java.io.Serializable

/**
 * The client data model
 *
 * The *Client* class holds methods for client connectivity features from & to the server.
 *
 * @property id optionally pass an ID if you want, defaults to -1
 * @constructor Creates a new *Client* instance
 * @since 0.0.4
 */
class Client(var id: Int = -1) : Serializable {

    val following: MutableList<Int> = mutableListOf<Int>()
    val followers: MutableList<Int> = mutableListOf<Int>()
    private val factory: PacketFactory = PacketFactory()

    fun dispatchFollowEvent(followerID: Int, destination: Server, callback: (success: Any?) -> Unit) {
        val packet: FollowUserPacket = this.factory.makePacket(PacketType.FOLLOW_USER) as FollowUserPacket
        packet.payload = FollowUserPacket.FollowUserPayload(this, followerID)
        sendToServer(packet, this, destination, callback)
    }

    fun dispatchRegisterEvent(destination: Server) {
        val packet: RegistrationPacket = this.factory.makePacket(PacketType.REGISTRATION) as RegistrationPacket
        packet.payload = RegistrationPacket.RegistrationPayload(this)
        sendToServer(packet, this, destination)
    }

    fun dispatchUploadEvent(image: String, destination: Server) {
        val packet: UploadImagePacket = this.factory.makePacket(PacketType.UPLOAD_IMAGE) as UploadImagePacket
        packet.payload = UploadImagePacket.UploadImagePayload(this, image)
        sendToServer(packet, this, destination)
    }

    fun dispatchListUsersEvent(destination: Server, callback: (usersIDs: Any?) -> Unit) {
        val packet: ListUsersPacket = this.factory.makePacket(PacketType.LIST_USER_IDS) as ListUsersPacket
        packet.payload = ListUsersPacket.ListUsersPayload(this)
        sendToServer(packet, this, destination, callback)
    }

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

}