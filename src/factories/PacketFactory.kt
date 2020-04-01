/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package factories

import enums.PacketType
import interfaces.Packet
import models.RegistrationPacket
import java.lang.Exception

class PacketFactory {

    @Throws(Exception::class)
    fun makePacket(type: PacketType): Packet {
        return when (type) {
            PacketType.REGISTRATION -> RegistrationPacket()
            else -> throw Exception("Type is not provided!")
        }
    }

}