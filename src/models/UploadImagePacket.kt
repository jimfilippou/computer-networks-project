/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import interfaces.Packet
import java.io.Serializable

class UploadImagePacket : Serializable, Packet {

    data class ImagePayload(val client: Client, val image: String) : Serializable

    override var payload: ImagePayload? = null

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

}

