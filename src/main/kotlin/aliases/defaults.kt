/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: defaults.kt
 */

package aliases

import models.packets.FollowUserPacket
import models.packets.ListUsersPacket
import models.packets.RegistrationPacket
import models.packets.UploadImagePacket


typealias rp = RegistrationPacket.RegistrationPayload
typealias lup = ListUsersPacket.ListUsersPayload
typealias uip = UploadImagePacket.UploadImagePayload
typealias fup = FollowUserPacket.FollowUserPayload