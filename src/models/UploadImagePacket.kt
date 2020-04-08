/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import interfaces.Packet
import java.io.Serializable

/**
 *
 * @since 0.0.3
 */
class UploadImagePacket : Serializable, Packet {

    data class UploadImagePayload(val sender: Client, val image: String) : Serializable

    override var payload: Any? = null

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

}

