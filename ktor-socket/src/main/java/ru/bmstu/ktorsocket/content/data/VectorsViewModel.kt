package ru.bmstu.ktorsocket.content.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.bmstu.ktorsocket.netw.Routes

// -> Координаты двух векторов размерности 3
// <- Булево значение: истина - если ортогональны, ложь - если не ортогональны

class VectorsViewModel(private val ktor: HttpClient): ViewModel() {
    private val _result = MutableStateFlow<Result>(Result.Unspecified)
    val result = _result.asStateFlow()

    private val _v1 = MutableStateFlow("")
    val v1 = _v1.asStateFlow()

    private val _v2 = MutableStateFlow("")
    val v2 = _v2.asStateFlow()

    fun sendData() {
        viewModelScope.launch {
            ktor.webSocket(
                urlString = "${Routes.TOTAL_WS}/${Routes.INPUT}",
            ) {
                inputMessages(v1.value, v2.value)
            }
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            ktor.webSocket("${Routes.TOTAL_WS}/${Routes.OUTPUT}") {
                outputMessages()
            }
        }
    }

    fun onV1Input(v1: String) {
        viewModelScope.launch {
            _v1.emit(v1)
            sendData()
        }
    }

    fun onV2Input(v2: String) {
        viewModelScope.launch {
            _v2.emit(v2)
            sendData()
        }
    }

    suspend fun DefaultClientWebSocketSession.outputMessages() {
        try {
            for (message in incoming) {
                message as? Frame.Text ?: continue
                val text = message.readText()
                Log.d("vvm", text)
                val result = Json.decodeFromString<Response>(message.readText())
//                _result.emit(if (result.res) Result.True else Result.False)
            }
        } catch (e: Exception) {
            println("Error while receiving: " + e.localizedMessage)
        }
    }

    suspend fun DefaultClientWebSocketSession.inputMessages(v1: String, v2: String) {
        val message = Data(v1, v2)
        try {
            send(Frame.Text(Json.encodeToString(message)))
            val r = receiveDeserialized<Response>()
            _result.emit(if (r.res == "true") Result.True else if (r.res == "false") Result.False else Result.Unspecified)
            Log.d("VM", r.toString())
        } catch (e: Exception) {
            println("Error while sending: " + e.localizedMessage)
            return
        }
    }
}
