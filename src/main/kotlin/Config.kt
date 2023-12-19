import com.google.gson.JsonArray
class Config {
    var tgBotName = ""
    var tgBotToken = ""
    var message: JsonArray = JsonArray()
}

val ConfigModle: String = "{\n" +
        "\t\"tgBotName\": \"\",\n" +
        "\t\"tgBotToken\": \"\",\n" +
        "\t\"message\": [\n" +
        "\t\t{\n" +
        "\t\t\t\"type\": \"help\",\n" +
        "\t\t\t\"keyWord\": [\"help\", \"/help\", \"start\", \"/start\", \"帮助\", \"/帮助\"],\n" +
        "\t\t\t\"intro\": \"色图机器人 for telegram, 仓库地址: https://github.com/xmexg/tgsetu\"\n" +
        "\t\t},\n" +
        "\t\t{\n" +
        "\t\t\t\"type\": \"image\",\n" +
        "\t\t\t\"keyWord\": [\"/hentai\", \"hentai\", \"/setu\", \"setu\", \"色图\", \"涩图\", \"来点色图\", \"来点涩图\", \"来张色图\", \"来张涩图\"],\n" +
        "\t\t\t\"url\": \"https://moe.jitsu.top/r18\",\n" +
        "\t\t\t\"intro\": \"R18图\"\n" +
        "\t\t},\n" +
        "\t\t{\n" +
        "\t\t\t\"type\": \"image\",\n" +
        "\t\t\t\"keyWord\": [\"/bg\", \"bg\", \"二次元\"],\n" +
        "\t\t\t\"url\": \"https://www.loliapi.com/bg/\",\n" +
        "\t\t\t\"intro\": \"随机二次元图\"\n" +
        "\t\t},\n" +
        "\t\t{\n" +
        "\t\t\t\"type\": \"image\",\n" +
        "\t\t\t\"keyWord\": [\"/pc\", \"pc\"],\n" +
        "\t\t\t\"url\": \"https://www.loliapi.com/acg/pc/\",\n" +
        "\t\t\t\"intro\": \"二次元电脑图\"\n" +
        "\t\t},\n" +
        "\t\t{\n" +
        "\t\t\t\"type\": \"image\",\n" +
        "\t\t\t\"keyWord\": [\"/pe\", \"pe\"],\n" +
        "\t\t\t\"url\": \"https://www.loliapi.com/acg/pe/\",\n" +
        "\t\t\t\"intro\": \"二次元手机图\"\n" +
        "\t\t},\n" +
        "\t\t{\n" +
        "\t\t\t\"type\": \"image\",\n" +
        "\t\t\t\"keyWord\": [\"/tx\", \"tx\", \"头像\"],\n" +
        "\t\t\t\"url\": \"https://www.loliapi.com/acg/pp/\",\n" +
        "\t\t\t\"intro\": \"二次元头像\"\n" +
        "\t\t}\n" +
        "\t]\n" +
        "}\n"
