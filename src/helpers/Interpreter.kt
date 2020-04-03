/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package helpers

import models.Client
import models.Server
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.system.exitProcess

/**
 * CLI tool to manage clients (EXPERIMENTAL)
 * This chunk of code is ugly, intentionally
 *
 * @param server the server to operate on
 */
fun interpret(server: Server) {

    val clients: MutableList<Client> = mutableListOf<Client>()
    var selected: Client? = null

    loop@ while (true) {
        if (selected == null) {
            print("-> ")
        } else {
            print("$selected -> ")
        }
        val input = Scanner(System.`in`)
        var command: String?
        command = input.next()
        when (command) {
            "new" -> {
                val c = Client()
                clients.add(c)
                println("A new client object was added to clients!\nClient: $c")
            }
            "selected" -> {
                println(selected)
            }
            "select" -> {
                for ((index, value) in clients.withIndex()) {
                    println("[$index] -> $value")
                }
                print("Choose client using ID: ")
                val i = Scanner(System.`in`)
                val reply = i.nextInt()
                selected = clients[reply]
            }
            "register" -> {
                if (selected == null) continue@loop
                selected.register(server)
            }
            "ls" -> {
                for (path in Files.list(Paths.get(System.getProperty("user.dir")))) {
                    println(path)
                }
            }
            "help" -> {
                if (selected == null) continue@loop
                println("1. register\n2. login")
            }
            "exit" -> {
                exitProcess(0)
            }
            else -> print("xd")
        }
    }
}