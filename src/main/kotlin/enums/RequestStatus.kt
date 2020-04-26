/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: RequestStatus.kt
 */

package enums

/**
 * The request status enumeration
 *
 * Used by the follow request packet, when a user first requests to follow someone else
 * A "Pending" type of request will be sent. When user accepts the request, type  becomes "Accepted"
 *
 * @since 1.2.0
 */
enum class RequestStatus {

    PENDING,
    ACCEPTED,
    REJECTED

}