/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package handlers

import models.Client
import models.Server

class ClientHandler(private val client: Client, private val server: Server) : Thread() {

    override fun run() {
        client.register(server)
    }

}
