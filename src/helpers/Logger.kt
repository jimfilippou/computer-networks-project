/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package helpers

import java.text.SimpleDateFormat

/**
 * A custom logger because it is easier to debug.
 * Every function is static and it supports various terminal colors
 */
object Logger {

    private const val reset = "\u001B[0m"
    private const val red = "\u001B[31m"
    private const val green = "\u001B[32m"
    private const val yellow = "\u001B[33m"
    private const val blue = "\u001B[34m"
    private const val purple = "\u001B[35m"
    private const val cyan = "\u001B[36m"
    private const val white = "\u001B[37m"

    private fun timestamp(): String {
        return SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(System.currentTimeMillis())
    }

    // Success
    fun success(message: String) {
        println("$green${timestamp()}: $message$reset")
    }

    // Info
    fun info(message: String) {
        println("$purple${timestamp()}: $message$reset")
    }

    // Error
    fun error(message: String) {
        println("$red${timestamp()}: $message$reset")
    }

    // Debug
    fun debug(message: String) {
        println("$blue${timestamp()}: $message$reset")
    }

    // Warning
    fun warn(message: String) {
        println("$yellow${timestamp()}: $message$reset")
    }

}