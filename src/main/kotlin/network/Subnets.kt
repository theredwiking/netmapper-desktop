package network

import kotlinx.coroutines.*
import java.io.Serializable
import java.lang.Exception
import kotlin.system.exitProcess

val subnetScope = MainScope()

data class Vlan(val active: List<Ping>, val inactive: List<Ping>): Serializable;

fun runScan(onResult: (List<Vlan>) -> Unit) {
    try {
        val info: Machine = deviceInfo();
        val baseIp: String = info.ip.split(".")[0];
        subnetScope.launch(Dispatchers.IO) {
            val subnets = scan(baseIp)
            onResult.invoke(subnets)
        }
        return;
    } catch (e: Exception) {
        println(e)
        exitProcess(1);
    }
}

private fun exists(baseIp: String, i: Int, onComplete: (Vlan) -> Unit) {
    val active: MutableList<Ping> = mutableListOf();
    val inactive: MutableList<Ping> = mutableListOf();
    for (j in 0..255){
        val ping: Ping = ping("$baseIp.$i.$j.1");
        if(ping.exist) {
            active.add(ping);
        } else {
            inactive.add(ping);
        }
    }
    onComplete(Vlan(active, inactive));
    return;
}

private suspend fun scan(baseIp: String): List<Vlan> {
    val result: MutableList<Vlan> = mutableListOf();
    var i = 0;
    if(baseIp == "192") {
        exists(baseIp, 168) {
            if(it.inactive.size < 256) {
                result.add(it)
            }
        }
    } else {
        for (j in 1..64) {
            runBlocking {
                val pings = listOf(
                    subnetScope.launch(Dispatchers.IO) {
                        exists(baseIp, i++) {
                            if(it.inactive.size < 256) {
                                result.add(it)
                            }
                        }
                    },
                    subnetScope.launch(Dispatchers.IO) {
                        exists(baseIp, i++) {
                            if(it.inactive.size < 256) {
                                result.add(it)
                            }
                        }
                    },
                    subnetScope.launch(Dispatchers.IO) {
                        exists(baseIp, i++) {
                            if(it.inactive.size < 256) {
                                result.add(it)
                            }
                        }
                    },
                    subnetScope.launch(Dispatchers.IO) {
                        exists(baseIp, i++) {
                            if(it.inactive.size < 256) {
                                result.add(it)
                            }
                        }
                    },
                )
                pings.joinAll();
            }
        }
    }
    return result;
}

fun totalList(result: List<Vlan>): List<Ping> {
    val total: MutableList<Ping> = mutableListOf();
    result.forEach{
        it.active.forEach{
            total.add(it);
        }
    }
    return total;
}