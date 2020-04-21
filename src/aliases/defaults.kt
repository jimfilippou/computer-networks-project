/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package aliases

import models.packets.FollowUserPacket
import models.packets.ListUsersPacket
import models.packets.RegistrationPacket
import models.packets.UploadImagePacket

/**
 * Aliases are used to make code smaller, because names are getting bigger and bigger.
 * If you feel confused, cmd + click or (cmd + click for mac) on an alias and it will bring you here!
 * @since 0.0.4
 */
typealias rp = RegistrationPacket.RegistrationPayload
typealias lup = ListUsersPacket.ListUsersPayload
typealias uip = UploadImagePacket.UploadImagePayload
typealias fup = FollowUserPacket.FollowUserPayload