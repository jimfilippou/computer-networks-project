/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import enums.PacketType
import factories.PacketFactory
import helpers.sendToServer
import java.io.Serializable

/**
 * The client data model, which also holds methods for client connectivity features from & to the server
 *
 * @param id optionally pass an ID if you want, defaults to -1
 * @since 0.0.3
 */
class Client(var id: Int = -1) : Serializable {

    private val following: MutableList<Int> = mutableListOf<Int>()
    private val followedBy: MutableList<Int> = mutableListOf<Int>()
    private val factory: PacketFactory = PacketFactory()

    fun follow(followerID: Int, destination: Server, callback: (success: Any?) -> Unit) {
        //this.following.add(followerID)
        val packet: FollowUserPacket = this.factory.makePacket(PacketType.FOLLOW_USER) as FollowUserPacket
        packet.payload = FollowUserPacket.FollowUserPayload(this, followerID)
        sendToServer(packet, this, destination, callback)
    }

    fun register(destination: Server) {
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