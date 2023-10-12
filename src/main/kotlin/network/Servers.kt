package network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.Serializable
import java.net.ConnectException
import java.net.InetSocketAddress
import java.net.Socket

data class ServerInfo(val ip: String, val ports: List<Int>): Serializable;
val serverScope = MainScope()

private fun portScan(ip: String): List<Int> {
    val result: MutableList<Int> = mutableListOf();
    val ports: List<Int> = listOf(21, 22, 53, 80, 443)
    ports.forEach {
        val socket = Socket();
        try {
            socket.connect(InetSocketAddress(ip, it), 500)
            result.add(it);
        } catch (_: ConnectException) {} catch (e: Exception) {
            println("Timeout: $e")
        }
        socket.close()
    }
    return result;
}

fun isServer(ip: String): ServerInfo {
    var ports: List<Int> = mutableListOf();
    val device: Ping = ping(ip);
    if(device.exist) {
        ports = portScan(ip);
    }
    return ServerInfo(ip, ports);
}

fun scanForServers(vlans: List<Ping>, onComplete: (List<ServerInfo>) -> Unit) {
    val servers: MutableList<ServerInfo> = mutableListOf()
    serverScope.launch(Dispatchers.IO) {
        vlans.forEach {
            for (i in 2..255) {
                val ip = it.ip.replace(Regex("(.)\\d{1,3}\$"), ".$i");
                val result: ServerInfo = isServer(ip);
                if(result.ports.isNotEmpty()) {
                    servers.add(result);
                }
            }
        }
        onComplete(servers);
    }
}