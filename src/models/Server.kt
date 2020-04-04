/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import helpers.Logger
import java.io.File
import java.io.ObjectOutputStream

class Server(var ip: String, var port: Int) {

    private var counter = 0
    private val registeredUserIDs: MutableList<Int> = mutableListOf<Int>()

    init {
        val storage = File("storage")
        if (storage.exists() && storage.isDirectory) {
            Logger.debug("Found old server storage files, deleting...")
            if (storage.delete()) {
                Logger.success("Storage was deleted.")
            } else {
                Logger.error("Storage was not deleted.")
            }
        } else {
            File("storage").mkdir()
        }
    }

    fun receivePacket(packet: Any, replyTo: ObjectOutputStream) {
        when (packet) {
            is RegistrationPacket -> {
                Logger.info("Received registration packet from -> ${packet.payload}")
                counter++
                this.registeredUserIDs.add(counter)
                Logger.debug("Creating directory \"c${packet.payload}\" for user")
                this.createUserDirectory(counter)
                Logger.debug("Writing $counter to socket: $replyTo")
                replyTo.writeObject(counter)
                // Logger.debug("Closing stream...")
                // replyTo.close()
            }
        }
    }

    private fun createUserDirectory(uid: Int) {
        File("storage\\c$uid").mkdir()
    }

    override fun toString(): String {
        return "Server"
    }


}