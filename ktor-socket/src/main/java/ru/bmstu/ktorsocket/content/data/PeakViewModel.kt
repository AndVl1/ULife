package ru.bmstu.ktorsocket.content.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.bmstu.ktorsocket.netw.Routes

class VectorsViewModel(private val ktor: HttpClient): ViewModel() {
    private val _result = MutableStateFlow<Result>(Result.Unspecified)
    val result = _result.asStateFlow()
    private val _status = MutableStateFlow(false)
    val status = _status.asStateFlow()
    private var socketSession: WebSocketSession? = null

    private val _str = MutableStateFlow("")
    val str = _str.asStateFlow()

    fun sendData() {
        viewModelScope.launch {
            if (status.value) {
                socketSession?.send(Frame.Text(Json.encodeToString(Data(str.value))))
            }
        }
    }

    private fun observeSockets() {
        try {
            viewModelScope.launch {
                try {
                    socketSession?.incoming
                        ?.receiveAsFlow()
                        ?.mapNotNull { it as? Frame.Text }
                        ?.map { it.readText() }
                        ?.map { Json.decodeFromString<Response>(it) }
                        ?.collect {
                            _result.emit(if (it.res == "true") Result.True else if (it.res == "false") Result.False else Result.Unspecified)
                        }
                } catch (e: Exception) {
                    _result.emit(Result.Unspecified)
                    _status.emit(false)
                }
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                _result.emit(Result.Unspecified)
                _status.emit(false)
            }
        }
    }

    fun initSockets() {
        viewModelScope.launch {
            try {
                socketSession = ktor.webSocketSession("${Routes.TOTAL_WS}/${Routes.GET_PEAK}")
            } catch (e: Exception) {
                _status.emit(false)
            }
            if (socketSession?.isActive == true) {
                _status.emit(true)
                observeSockets()
            } else {
                _status.emit(false)
            }
        }
    }

    fun onStrInput(str: String) {
        viewModelScope.launch {
            _str.emit(str)
            sendData()
        }
    }
}
