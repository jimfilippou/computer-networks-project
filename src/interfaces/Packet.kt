/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package interfaces

import enums.PacketType

/*
 * The packet interface, which is part of the factory design pattern
 */
interface Packet {

    val payload: Any?

}