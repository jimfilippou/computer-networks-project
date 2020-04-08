/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package factories

import enums.PacketType
import interfaces.Packet
import models.RegistrationPacket
import models.UploadImagePacket

/**
 * The packet factory.
 *
 * Creates packet objects that handle appropriate data.
 */
class PacketFactory {

    @Throws(Exception::class)
    fun makePacket(type: PacketType): Packet {
        return when (type) {
            PacketType.REGISTRATION -> RegistrationPacket()
            PacketType.UPLOAD_IMAGE -> UploadImagePacket()
            else -> throw Exception("Type is not provided!")
        }
    }

}