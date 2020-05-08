/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: Interpreter.kt
 */

package helpers

import enums.PostType
import models.Client
import models.Post
import models.Server
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.system.exitProcess

operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)

/**
 * CLI tool to manage clients (EXPERIMENTAL)
 *
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
            "register many" -> {
                val o = (5..10).random()
                for (user: Int in 1..o) {
                    val c = Client()
                    clients.add(c)
                    c.dispatchRegisterEvent(server)
                }
            }
            "new" -> {
                val c = Client()
                clients.add(c)
                println("A new client object was added to clients!\nClient: $c")
            }
            "selected" -> {
                println(selected)
            }
            in Regex("^new\\s\\d+") -> {
                val howMany = command.split(" ")[1].toInt()
                for (user: Int in 1..howMany) {
                    val c = Client()
                    clients.add(c)
                    c.dispatchRegisterEvent(server)
                }
            }
            in Regex("^use\\s\\d+") -> {
                val index = command.split(" ")[1].toInt() - 1
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
            "show requests" -> {
                if (selected == null) continue@loop
                selected.dispatchGetFollowRequestsEvent(server) { results ->
                    println(results)
                }
            }
            in Regex("^reject\\s\\d+") -> {
                if (selected == null) continue@loop
                val index = command.split(" ")[1].toInt()
                selected.dispatchRejectFollowRequestEvent(server, index) { success ->
                    if (success == true) {
                        println("You successfully rejected the follow event!")
                    }
                }
            }
            in Regex("^accept\\s\\d+") -> {
                if (selected == null) continue@loop
                val index = command.split(" ")[1].toInt()
                selected.dispatchAcceptFollowRequestEvent(server, index) { success ->
                    if (success == true) {
                        println("You successfully accepted the follow event!")
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
                        print("Give absolute path of the image you want to upload, the name and the description: ")
                        val p = Scanner(System.`in`)
                        val path = p.next()
                        val name = p.next()
                        val desc = p.next()
                        copyFileUsingStream(File(path), File("storage/c${selected.id}/$name"))
                        val post = Post(selected, PostType.MULTIMEDIA, name, desc)
                        selected.dispatchUploadEvent(server, post)
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
