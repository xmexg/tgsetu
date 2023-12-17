# tgsetu
[Telegram的色图机器人](https://t.me/setuDaily_bot)  

# 开始使用前
<b>安装java</b>  

+ ubuntu: `sudo apt install openjdk-17-jdk`  
+ windows: 前往[oracle](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)手动下载安装  

<b>申请tg机器人</b>

+ 添加 https://t.me/BotFather 为好友  
+ 如图所示即可创建机器人  
![image](./image/2.png)


# 快速安装
1. [下载jar文件](https://github.com/xmexg/tgsetu/releases)  
2. cd到jar文件所在目录,使用 `java -jar 文件名.jar` 运行  
3. 初次运行会生成 `config.json` 配置文件,编辑该json文件并再次运行jar
![image](./image/1.png)

# 从源码构建
+ ## 安装 `mvn`  
    + <b>ubuntu</b>: `sudo apt install maven`  
    + <b>windows</b>:  
        - 手动安装: 在[官网](https://maven.apache.org/download.cgi)下载并配置环境变量   
        - 命令安装:  
                1. `使用win + r`输入`powershell`打开终端  
                2. 依次执行  
                    `Set-ExecutionPolicy Bypass -Scope Process -Force`   
                    `iwr https://chocolatey.org/install.ps1 -UseBasicParsing | iex`   
                    `choco install maven`  
                    完成安装
+ ## 开始构建
    `mvn clean package`
    ```
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  14.407 s
    [INFO] Finished at: 2023-12-17T17:08:57+08:00
    [INFO] ------------------------------------------------------------------------
    ```

+ ## 运行jar
    + `cd target`  
    + `java -jar tgst-1.0-SNAPSHOT-jar-with-dependencies.jar`


# config.json配置文件
    ``` 
    {
        "keyWord": ["触发命令的关键词", "/setu"],
        "setuApi": "https://moe.jitsu.top/r18",
        "tgBotName": "你的机器人名",
        "tgBotToken": "你的机器人token"
    }
    ```