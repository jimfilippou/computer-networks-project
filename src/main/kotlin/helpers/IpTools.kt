/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: IpTools.kt
 */

package helpers

import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

/**
 * Reads all network interfaces, picks the IPv4 address
 *
 * @throws RuntimeException
 * @since 0.0.1
 * @return The LAN IPv4 address
 */
@Throws(java.lang.RuntimeException::class)
fun getIPv4Address(): String {
    try {
        val interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements()) {
            val `interface` = interfaces.nextElement()
            // filters out 127.0.0.1 and inactive interfaces
            if (`interface`.isLoopback || !`interface`.isUp) continue
            val addresses = `interface`.inetAddresses
            while (addresses.hasMoreElements()) {
                val addr = addresses.nextElement()
                if (addr is Inet4Address) {
                    return addr.hostAddress
                }
            }
        }
    } catch (e: SocketException) {
        throw RuntimeException(e)
    }
    return ""
}