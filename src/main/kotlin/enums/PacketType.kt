/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: PacketType.kt
 */

package enums

/**
 * The packet enumeration
 *
 * Contains the type of each packet which correspond to a specific action within the application
 *
 * @see models.packets
 */
enum class PacketType {

    REGISTRATION,
    UPLOAD_IMAGE,
    LIST_USER_IDS,
    FOLLOW_USER,
    GET_FOLLOW_REQUESTS,
    REJECT_FOLLOW_REQUEST,
    ACCEPT_FOLLOW_REQUEST,
    CREATE_POST,
    SHOW_POST_OF_X

}