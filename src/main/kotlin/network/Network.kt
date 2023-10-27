package network

import java.io.Serializable
import java.net.*
import java.util.Enumeration
import kotlin.Exception

data class Machine (val os: String, val ip: String, val name: String);
data class Ping (val ip: String, val exist: Boolean): Serializable;

fun deviceInfo(): Machine {
    val os: String = System.getProperty("os.name")
    val ip: String = deviceIp();
    val name: String = InetAddress.getLocalHost().hostName;
    return Machine(os, ip, name);
}

fun deviceIp(): String {
    var ip = "";
    val temp: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces();
    while (temp.hasMoreElements()) {
        val n: NetworkInterface = temp.nextElement();
        val test: Enumeration<InetAddress> = n.inetAddresses;
        while (test.hasMoreElements()) {
            val i: InetAddress = test.nextElement();
            val tempIp: String = i.hostAddress;
            if(!tempIp.contains("127.0") && !tempIp.contains(":")) {
                ip = tempIp
            }
        }
    }
    return ip;
}

fun ping(ip: String): Ping {
    try {
        val exist: Boolean = InetAddress.getByName(ip).isReachable(200);
        return Ping(ip, exist);
    } catch (e: Exception) {
        println(e)
        return Ping(ip, false);
    }
}