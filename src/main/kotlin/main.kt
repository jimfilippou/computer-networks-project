/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: main.kt
 */

import threads.ServerConnectionReceiver
import helpers.fetchClients
import helpers.getIPv4Address
import helpers.interpret
import models.Client
import models.Server

object Main {

    @JvmStatic
    fun main(args: Array<String>) {

        // $ ~ ifconfig | grep "inet " | grep -v 127.0.0.1 | awk '{print $2}'
        val ip = getIPv4Address()
        val port = 9090

        when (args[0]) {
            "server" -> {
                val instance = Server(ip, port)
                instance.setup()
                val server = ServerConnectionReceiver(instance)
                server.start()
            }
            "interpreter" -> {
                val default = Server(ip, port)
                interpret(default)
            }
        }


    }

}
