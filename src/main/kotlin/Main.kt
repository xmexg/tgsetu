import com.google.gson.Gson
import com.google.gson.JsonObject
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
var MESSAGETYPE_MAP = HashMap<String, Int>()
var VERSION = Main::class.java.`package`.implementationVersion ?: "2.0"

fun main() {
    start()
}

class Main {}

fun start() {
    println("随机色图 for telegram!")
    try {
        val gson = Gson()
        val reader = FileReader("config.json")
        CONFIG = gson.fromJson(reader, Config::class.java)
        reader.close()
        updateMessageMap()
    } catch (e: IOException) {
        val writer = java.io.FileWriter("config.json")
        writer.write(ConfigModle)
        writer.close()
        println("请在config.json中填写配置")
        return
    }
    println("CONFIG json: " + Gson().toJson(CONFIG))

    try {
        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
        telegramBotsApi.registerBot(setuBot())
    } catch (e: TelegramApiException) {
        throw RuntimeException(e)
    }
}

/**
 * MESSAGE_MAP格式为<CONFIG.message.[].keyWord[], 该关键词在CONFIG.message.[]中的位置>
 */
fun updateMessageMap() {
    MESSAGETYPE_MAP.clear()
    CONFIG.message.forEachIndexed { index, jsonElement ->
        jsonElement.asJsonObject["keyWord"].asJsonArray.forEach { // 遍历keyWord
            MESSAGETYPE_MAP[it.asString] = index
        }
    }
    println("MESSAGETYPE_MAP: $MESSAGETYPE_MAP")
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
                MESSAGETYPE_MAP[text]?.let {//  如果MESSAGE_MAP[text]不为空, 则执行{}
                    val messageConf: JsonObject = CONFIG.message[it].asJsonObject // 这里的it是MESSAGE_MAP[text]
                    when (messageConf["type"].asString) {
                        "help" -> {
                            sendHelp(chatId.toString())
                        }
                        "image" -> {
                            sendImage(chatId.toString(), messageConf["url"].asString)
                        }
                    }
                }
            }
        }

        fun sendHelp(chatId: String) {
            val sendText = SendMessage()
            sendText.chatId = chatId
            sendText.chatId = chatId
            var textContent= ""
            CONFIG.message.forEach {
                textContent += it.asJsonObject["keyWord"].asJsonArray
                textContent += "\n"
                textContent += it.asJsonObject["intro"].asString + "\n\n"
            }
            textContent  += "版本: $VERSION"
            sendText.text = textContent
            execute(sendText)
        }

        fun sendImage(chatId: String, url: String) {
            print("发送图片\t")
            val sendPhoto = SendPhoto()
            sendPhoto.chatId = chatId
            // okhttp3使用get访问https://moe.jitsu.top/r18, 返回重定向后的url
            val client = okhttp3.OkHttpClient()
            val request = okhttp3.Request.Builder().url(url).build()
            var imageUrl = ""
            try {
                val response = client.newCall(request).execute()  // 这里会抛出IOException, 因为https://moe.jitsu.top/r18返回的是302重定向
                imageUrl = response.request.url.toString()
                println("imageUrl:$imageUrl")
                sendPhoto.photo = InputFile(imageUrl)
                execute(sendPhoto)
            } catch (e: IOException) {
                e.printStackTrace()
                val sendText = SendMessage()
                sendText.chatId = chatId
                sendText.text = imageUrl.ifEmpty { e.toString() } // 如果imageUrl为空, 则发送e.toString(), 否则发送imageUrl
                execute(sendText)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
                val sendText = SendMessage()
                sendText.chatId = chatId
                sendText.text = imageUrl.ifEmpty { e.toString() } // 如果imageUrl为空, 则发送e.toString(), 否则发送imageUrl
                execute(sendText)
            }
        }
    }
}
