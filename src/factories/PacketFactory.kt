/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package factories

import enums.PacketType
import interfaces.Packet
import models.ListUsersPacket
import models.RegistrationPacket
import models.UploadImagePacket
import java.io.Serializable

/**
 * The packet factory.
 *
 * Creates packet objects that handle appropriate data.
 */
class PacketFactory: Serializable {

    @Throws(Exception::class)
    fun makePacket(type: PacketType): Packet {
        return when (type) {
            PacketType.REGISTRATION -> RegistrationPacket()
            PacketType.UPLOAD_IMAGE -> UploadImagePacket()
            PacketType.LIST_USER_IDS -> ListUsersPacket()
            else -> throw Exception("Type is not provided!")
        }
    }

}