/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: Client.kt
 */

package models

import enums.PacketType
import factories.PacketFactory
import helpers.sendToServer
import models.packets.*
import java.io.Serializable

/**
 * The client data model
 *
 * The *Client* class holds methods for client connectivity features from & to the server.
 *
 * @property id optionally pass an ID if you want, defaults to -1
 * @constructor Creates a new *Client* instance
 * @since 1.2.1
 */
class Client(var id: Int = -1) : Serializable {

    val following: MutableList<Int> = mutableListOf<Int>()
    val followers: MutableList<Int> = mutableListOf<Int>()
    val followRequests: MutableList<FollowRequest> = mutableListOf<FollowRequest>()
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

    fun dispatchUploadEvent(destination: Server, post: Post, callback: (message: Any?) -> Unit) {
        val packet: UploadPostPacket = this.factory.makePacket(PacketType.UPLOAD_IMAGE) as UploadPostPacket
        packet.payload = UploadPostPacket.UploadPostPayload(this, post)
        sendToServer(packet, this, destination, callback)
    }

    fun dispatchListUsersEvent(destination: Server, callback: (usersIDs: Any?) -> Unit) {
        val packet: ListUsersPacket = this.factory.makePacket(PacketType.LIST_USER_IDS) as ListUsersPacket
        packet.payload = ListUsersPacket.ListUsersPayload(this)
        sendToServer(packet, this, destination, callback)
    }

    fun dispatchGetFollowRequestsEvent(destination: Server, callback: (requests: Any?) -> Unit) {
        val packet: GetFollowRequestsPacket = this.factory.makePacket(PacketType.GET_FOLLOW_REQUESTS) as GetFollowRequestsPacket
        packet.payload = GetFollowRequestsPacket.GetFollowRequestsPayload(this)
        sendToServer(packet, this, destination, callback)
    }

    fun dispatchRejectFollowRequestEvent(destination: Server, requestID: Int, callback: (success: Any?) -> Unit) {
        val packet: RejectFollowRequestPacket = this.factory.makePacket(PacketType.REJECT_FOLLOW_REQUEST) as RejectFollowRequestPacket
        packet.payload = RejectFollowRequestPacket.RejectFollowRequestPayload(this, requestID)
        sendToServer(packet, this, destination, callback)
    }

    fun dispatchAcceptFollowRequestEvent(destination: Server, requestID: Int, callback: (success: Any?) -> Unit) {
        val packet: AcceptFollowRequestPacket = this.factory.makePacket(PacketType.ACCEPT_FOLLOW_REQUEST) as AcceptFollowRequestPacket
        packet.payload = AcceptFollowRequestPacket.AcceptFollowRequestPayload(this, requestID)
        sendToServer(packet, this, destination, callback)
    }

    fun dispatchShowPostOfXEvent(destination: Server, ID: Int, callback: (posts: Any?) -> Unit) {
        val packet: ShowPostOfXPacket = this.factory.makePacket(PacketType.SHOW_POST_OF_X) as ShowPostOfXPacket
        packet.payload = ShowPostOfXPacket.ShowPostOfXPacketPayload(this, ID)
        sendToServer(packet, this, destination, callback)
    }

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

    override fun toString(): String {
        return "Client ${this.id} ☜(ˆ▿ˆc)"
    }

}