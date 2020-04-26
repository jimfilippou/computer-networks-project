/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: Request.kt
 */

package models

import enums.RequestStatus
import java.io.Serializable


data class FollowRequest(var from: Client, var status: RequestStatus): Serializable {}