/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: ListUsersPacket.kt
 */

package models.packets

import interfaces.Packet
import java.io.Serializable

/**
 *
 * @since 0.0.3
 */
class ListUsersPacket: Serializable, Packet {

    data class ListUsersPayload(val sender: Serializable, val userIDs: MutableList<Int>? = null): Serializable

    override var payload: Any? = null

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

}