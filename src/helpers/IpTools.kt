/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package helpers

import java.net.*

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