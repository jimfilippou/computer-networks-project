/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: PacketFactory.kt
 */

package factories

import enums.PacketType
import interfaces.Packet
import models.packets.*
import java.io.Serializable

/**
 * The packet factory.
 *
 * Creates packet objects that handle appropriate data.
 *
 * @since 0.0.4
 */
class PacketFactory : Serializable {

    @Throws(Exception::class)
    fun makePacket(type: PacketType): Packet {
        return when (type) {
            PacketType.REGISTRATION -> RegistrationPacket()
            PacketType.UPLOAD_IMAGE -> UploadImagePacket()
            PacketType.LIST_USER_IDS -> ListUsersPacket()
            PacketType.FOLLOW_USER -> FollowUserPacket()
            PacketType.GET_FOLLOW_REQUESTS -> GetFollowRequestsPacket()
            else -> throw Exception("Type is not provided for the factory!")
        }
    }

}