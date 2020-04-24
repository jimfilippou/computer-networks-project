/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: Interpreter.kt
 */

package helpers

import models.Client
import models.Server
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.system.exitProcess

operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)

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
        command = input.nextLine()
        when (command) {
            "new" -> {
                val c = Client()
                clients.add(c)
                println("A new client object was added to clients!\nClient: $c")
            }
            "selected" -> {
                println(selected)
            }
            in Regex("^use\\s\\d+") -> {
                val index = command.split(" ")[1].toInt()
                try {
                    selected = clients[index]
                } catch (err: IndexOutOfBoundsException) {
                    Logger.warn("Index was outside the bounds, recovering...")
                    continue@loop
                }
            }
            in Regex("^follow\\s\\d+") -> {
                val index = command.split(" ")[1].toInt()
                if (selected == null) continue@loop
                selected.dispatchFollowEvent(index, server) { status ->
                    if (status == true) {
                        print("Everything OK")
                    } else {
                        print("not ok")
                    }
                }
            }
            "register" -> {
                if (selected == null) continue@loop
                selected.dispatchRegisterEvent(server)
            }
            "ls" -> {
                for (path in Files.list(Paths.get(System.getProperty("user.dir")))) {
                    println(path)
                }
            }
            "list" -> {
                println(clients)
            }
            "remote list" -> {
                if (selected == null) continue@loop
                selected.dispatchListUsersEvent(server) { results ->
                    println(results)
                }
            }
            "upload" -> {
                if (selected == null) continue@loop
                if (selected.id == -1) {
                    Logger.warn("User is not logged in!")
                    continue@loop
                }
                print("1) Post\n 2) Image\nWhat would you like to upload? :")
                val i = Scanner(System.`in`)
                when (i.nextInt()) {
                    1 -> {
                        print("This is not supported YET")
                    }
                    2 -> {
                        print("Give absolute path of the image you want to upload and the name: ")
                        val p = Scanner(System.`in`)
                        val path = p.next()
                        val name = p.next()
                        copyFileUsingStream(File(path), File("storage/c${selected.id}/$name"))
                        selected.dispatchUploadEvent(name, server)
                    }
                    else -> print("Unrecognized command")
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

@Throws(IOException::class)
fun copyFileUsingStream(source: File, destination: File) {
    var `is`: InputStream? = null
    var os: OutputStream? = null
    try {
        `is` = FileInputStream(source)
        os = FileOutputStream(destination)
        val buffer = ByteArray(1024)
        var length: Int
        while (`is`.read(buffer).also { length = it } > 0) {
            os.write(buffer, 0, length)
        }
    } finally {
        `is`?.close()
        os?.close()
    }
}