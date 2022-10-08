package ru.bmstu.ktorsocket.netw

object Routes {
    const val HTTP = "http"
    const val WS = "ws"
    const val IP = "37.139.41.254"
    const val LOCAL = "10.0.2.2"
    const val PORT = "8081"
    const val INPUT = "num/input"
    const val OUTPUT = "num/output"
    const val TOTAL = "$HTTP://$IP:$PORT"
    const val TOTAL_WS = "$WS://$LOCAL:$PORT"
}
