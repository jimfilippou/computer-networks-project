/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import helpers.Logger
import java.io.File
import java.io.Serializable

/**
 * Server configuration & data object
 *
 * @since 0.0.1
 */
class Server(var ip: String, var port: Int): Serializable {

    var slaves: Int = 0
    val maxSlaves: Int = 7
    var counter: Int = 0
    val registeredUsers: HashMap<Int, Client> = hashMapOf<Int, Client>()

    fun setup() {
        val storage = File("storage")

        if (storage.exists() && storage.isDirectory) {
            Logger.debug("Found old server storage files, deleting...")

            val children: Array<String> = storage.list()
            for (i in children.indices) {
                File(storage, children[i]).delete()
            }

            Logger.success("Storage was successfully deleted.")
            Logger.debug("Creating new storage files...")
            File("storage").mkdir()
            File("storage/server").mkdir()

        } else {
            Logger.debug("Creating new storage files...")
            File("storage").mkdir()
            File("storage/server").mkdir()
        }
    }

    override fun toString(): String {
        return "Server"
    }


}