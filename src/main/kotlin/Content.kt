package fd

import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser

interface Content {
    fun execute(): String
}

abstract class AbstractContent : Content {
    val rmap: Map<String, String> = mapOf("&" to "&amp;")

    public fun adjust(oUrl: String): String {
        return rmap.entries.fold(oUrl) { url, (key, value) ->
            url.replace(key, value)
        }
    }
}

class HtmlContent(val urlInfo: URLInfo) : AbstractContent() {

    private var data: ByteArray? = null
    private val replaceMap = mutableMapOf<String, String>()


    private val handler = object : KsoupHtmlHandler {
        override fun onOpenTag(name: String, attributes: Map<String, String>, isImplied: Boolean) {
            when (name) {
                "img" -> {
                    attributes["src"]?.let { path ->
                        val uInfo  = Converter.convert(path, urlInfo)
                        val localPath = ImageContent(uInfo).execute()
                        replaceMap.put(path, localPath)
                    }
                }
                "script" -> {
                    attributes["src"]?.let { path ->
                        val uInfo  = Converter.convert(path, urlInfo)
                        println("js = ${uInfo.getURL()}")
                        val localPath = JsContent(uInfo).execute()
                        replaceMap.put(adjust(path), localPath)
                        //replaceMap.put(path, localPath)
                    }
                }
                "link" -> {
                    attributes["rel"]?.let { relname ->
                        if (relname == "stylesheet") {
                            attributes["href"]?.let { href ->
                                val uInfo = Converter.convert(href, urlInfo)
                                println("css path = ${uInfo.getURL()}")
                                var localPath = CssContent(uInfo).execute()
                                replaceMap.put(href, localPath)
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }

    private val parser: KsoupHtmlParser = KsoupHtmlParser(handler = this.handler)

    override fun execute(): String {
        println("url = ${urlInfo.getURL()}")

        // load from file and parse content
        /*
        Downloader.downloadf("index.html")?.let {
            this.data = it          // keep content a variable
            val content = String(it)
            parser.write(content)
            parser.end()
        }

         */

        // download and parse content
        Downloader.download(urlInfo.getURL())?.also {result ->
            result.first?.let {
                this.data = it
                val content = String(it)
                parser.write(content)
                parser.end()
            }
        }


        // save data
        val localPath = this.data?.let {
            //var html = String(it)

            val html = replaceMap.entries.fold(String(it)) { content, (key, value) ->
                content.replace(key, value)
            }
            /*
            replaceMap.forEach { key, value ->
                if (html.contains(key)) {
                    println("replacable: $key")
                } else {
                    println("cannot find: $key")
                }
                html = html.replace(key, value)
            }
             */

            Serializer.save("index2.html", html.toByteArray(), ContentType.HTML)
        }?: ""
        return localPath
    }
}
class ImageContent(val urlInfo: URLInfo) : AbstractContent() {
    override fun execute(): String {
        println("Download Image: ${urlInfo.getURL()} ...")

        var localPath = ""
        Downloader.download((urlInfo.getURL())).also { result ->
            result.first?.let {
                //println("result = ${result.second}")
                localPath = Serializer.save(urlInfo.path, it, result.second) // save with extension
            }
        }
        return localPath
    }
}

class CssContent(val urlInfo: URLInfo) : AbstractContent() {
     override fun execute(): String {
         println("Download CSS: ${urlInfo.getURL()} ...")

         var localPath = ""
         Downloader.download((urlInfo.getURL())).also { result ->
             result.first?.let {
                 localPath = Serializer.save(urlInfo.path, it, result.second) // save with extension
             }
         }
         return localPath
     }
 }

class JsContent(val urlInfo: URLInfo) : Content {
    override fun execute(): String {
        println("Download JS: ${urlInfo.getURL()} ...")

        var localPath = ""
        Downloader.download((urlInfo.getURL())).also { result ->
            result.first?.let {
                localPath = Serializer.save(urlInfo.path, it, result.second) // save with extension
            }
        }
        return localPath
    }
}