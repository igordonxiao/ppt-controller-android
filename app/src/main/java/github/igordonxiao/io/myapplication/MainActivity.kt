package github.igordonxiao.io.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val url = "ws://pptcontroller.herokuapp.com/ws"
    val webSocket = OkHttpClient.Builder()
            .readTimeout(3000, TimeUnit.SECONDS)
            .writeTimeout(3000, TimeUnit.SECONDS)
            .connectTimeout(3000, TimeUnit.SECONDS)
            .build().newWebSocket(Request.Builder().url(url).build(), object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            webSocket.send("android: subscription")
        }

        override fun onMessage(webSocket: WebSocket, text: String) = when (text) {
            "cmd:up" -> {

            }
            "cmd:down" -> {

            }
            else -> {
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) = showToast(bytes.toString())
        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) = showToast("连接关闭")
        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) = showToast("接关闭：$reason")
        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response) = showToast("连接失败，请重试")
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (findViewById(R.id.cmdUp) as Button).apply { setOnClickListener{webSocket.send("cmd:up")} }
        (findViewById(R.id.cmdDown) as Button).apply { setOnClickListener { webSocket.send("cmd:down") } }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
