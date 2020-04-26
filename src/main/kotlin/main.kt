/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: main.kt
 */
package main

import threads.ServerConnectionReceiver
import helpers.getIPv4Address
import helpers.interpret
import models.Server

fun main(args: Array<String>) {

    // $ ~ ifconfig | grep "inet " | grep -v 127.0.0.1 | awk '{print $2}'
    val ip = getIPv4Address()
    val port = 9090

    when (args[0]) {
        "server" -> {
            val instance = Server(ip, port)
            instance.setup()
            ServerConnectionReceiver(instance).start()
        }
        "interpreter" -> {
            interpret(Server(ip, port))
        }
    }


}