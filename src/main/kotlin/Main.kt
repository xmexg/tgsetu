import com.google.gson.Gson
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.io.FileReader
import java.io.IOException

var CONFIG = Config()
fun main() {
    println("随机色图 for telegram!")
    // 如果有config.json文件，读取配置, 没有就把配置写入config.json
    try {
        val gson = Gson()
        val reader = FileReader("config.json")
        CONFIG = gson.fromJson(reader, Config::class.java)
        reader.close()
    } catch (e: IOException) {
        val gson = Gson()
        val writer = java.io.FileWriter("config.json")
        gson.toJson(CONFIG, writer)
        writer.close()
        println("请在config.json中填写配置")
        return
    }
    // 输出CONFIG json
    println("CONFIG json: " + Gson().toJson(CONFIG))

    try {
        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
        telegramBotsApi.registerBot(setuBot())
    } catch (e: TelegramApiException) {
        throw RuntimeException(e)
    }
}

class Config {
    var keyWord = listOf("/setu", "setu", "色图", "涩图", "色图来", "涩图来", "来张色图", "来张涩图")
    var tgBotToken: String = ""
    var tgBotName: String = ""
    var setuApi: String = "https://moe.jitsu.top/r18"
}
fun setuBot(): LongPollingBot {
    return object : TelegramLongPollingBot() {
        override fun getBotUsername(): String {
            return CONFIG.tgBotName
        }

        override fun getBotToken(): String {
            return CONFIG.tgBotToken
        }

        override fun onUpdateReceived(update: Update) {
            if (update.hasMessage() && update.message.hasText()) {
                val message = update.message
                val chatId = message.chatId
                val text = message.text
                println("收到消息:$text")
                if (CONFIG.keyWord.contains(text)) {
                    print("发送色图\t")
                    val sendPhoto = SendPhoto()
                    sendPhoto.chatId = chatId.toString()
                    // okhttp3使用get访问https://moe.jitsu.top/r18, 返回重定向后的url
                    val client = okhttp3.OkHttpClient()
                    val request = okhttp3.Request.Builder().url(CONFIG.setuApi).build()
                    var url = ""
                    try {
                        val response = client.newCall(request).execute()
                        url = response.request.url.toString()
                        println("setu url:$url")
                        sendPhoto.photo = InputFile(url)
                        execute(sendPhoto)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        val sendText = SendMessage()
                        sendText.chatId = chatId.toString()
                        sendText.text = if (url.isEmpty()) e.toString() else url
                        execute(sendText)
                    }
                }
            }
        }
    }
}
