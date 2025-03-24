package fd

import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser

interface Content {
    fun execute(): String
    fun getOriginalUrl(): String
}

abstract class AbstractContent(open val urlInfo: URLInfo) : Content {
    val rmap: Map<String, String> = mapOf("&" to "&amp;")
    val contentsList = mutableListOf<Content>()

    fun adjust(oUrl: String): String {
        return rmap.entries.fold(oUrl) { url, (key, value) ->
            url.replace(key, value)
        }
    }
    override fun getOriginalUrl(): String = urlInfo.ourl

}

class HtmlContent(override val urlInfo: URLInfo) : AbstractContent(urlInfo) {

    private var data: ByteArray? = null
    private val replaceMap = mutableMapOf<String, String>()
    private val stack = mutableListOf<Pair<URLInfo, Int>>()

    private var level = 0

    constructor(urlInfo: URLInfo, level: Int): this(urlInfo) {
        this.level = level
    }


    private val handler = object : KsoupHtmlHandler {
        override fun onOpenTag(name: String, attributes: Map<String, String>, isImplied: Boolean) {
            when (name) {
                "source" -> {
                    attributes["type"]?.let {_type ->
                        if (_type == "image/webp") {
                            attributes["srcset"]?.let { path ->
                                if (path.trim() != "") {
                                    val uInfo = Converter.convert(path, urlInfo)
                                    contentsList.addLast(ImageContent(uInfo))
                                    //val localPath = ImageContent(uInfo).execute()
                                    //replaceMap.put(adjust(path), localPath)
                                }
                            }
                        }
                    }
                }
                "img" -> {
                    attributes["src"]?.let { path ->
                        if (path.trim() != "") {
                            val uInfo = Converter.convert(path, urlInfo)
                            contentsList.addLast(ImageContent(uInfo))
                            //val localPath = ImageContent(uInfo).execute()
                            //replaceMap.put(adjust(path), localPath)
                        }
                    }
                    attributes["srcset"]?.let {paths ->
                        //println("srcset = $paths")
                        if (paths.trim() != "" && !paths.startsWith("data:image")) {
                            paths.split(",").forEach { path ->
                                //val uInfo =  path.trim().split(" ")[0].let { Converter.convert(it, urlInfo) }
                                val oldPath = path.trim().split(" ")[0]
                                val uInfo = Converter.convert(oldPath, urlInfo)
                                contentsList.addLast(ImageContent(uInfo))
                                //val localPath = ImageContent(uInfo).execute()
                                //replaceMap.put(adjust(oldPath), localPath)
                            }
                        }
                    }
                }
                "script" -> {
                    attributes["src"]?.let { path ->
                        //println("script = $path")
                        if (path.trim() != "") {
                            val uInfo = Converter.convert(path, urlInfo)
                            contentsList.addLast(JsContent(uInfo))
                            //val localPath = JsContent(uInfo).execute()
                            //replaceMap.put(path, localPath) // for uncontrollable descriptions of authors
                            //replaceMap.put(adjust(path), localPath)
                        }
                    }
                }
                "link" -> {
                    attributes["rel"]?.let { relname ->
                        if (relname == "stylesheet") {
                            attributes["href"]?.let { href ->
                                //println("css = $href")
                                if (href.trim() != "") {
                                    val uInfo = Converter.convert(href, urlInfo)
                                    contentsList.addLast((CssContent(uInfo)))
                                    //val localPath = CssContent(uInfo).execute()
                                    //replaceMap.put(adjust(href), localPath)
                                }
                            }
                        }
                    }
                }
                "a" -> {
                    if (level > 0) {
                        attributes["href"]?.let { path ->
                            //println("a $path")
                            if (!path.startsWith("#") && path.trim() != "") {
                                val uInfo = Converter.convert(path, urlInfo)
                                contentsList.addLast(HtmlContent(uInfo, level-1))
                                //val localPath = HtmlContent(uInfo, level-1).execute()
                                //replaceMap[adjust(path)] = localPath
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
        println("url = ${urlInfo.getURL()}, level = ${level}")

        // download and parse content using HTML parser
        Downloader.download(urlInfo.getURL()).also { result ->
            result.first?.let {
                this.data = it
                val content = String(it)
                parser.write(content)
                parser.end()
            }
        }

        // execute downloading other data
        contentsList.forEach { content ->  replaceMap[adjust(content.getOriginalUrl())] = content.execute() }

        // save data
        val localPath = this.data?.let {
            //var html = String(it)

            val html = replaceMap.entries.fold(String(it)) { content, (key, value) ->
                content.replace(key, value)
            }
            Serializer.save(urlInfo, html.toByteArray(), ContentType.HTML)
        }?: ""

        return localPath
    }
}
class ImageContent(override val urlInfo: URLInfo) : AbstractContent(urlInfo) {
    override fun execute(): String {
        //println("Download Image: ${urlInfo.getURL()} ...")
        /* embedded data */
        if (urlInfo.embed) return urlInfo.path

        var localPath = ""
        Downloader.download((urlInfo.getURL())).also { result ->
            result.first?.let {
                //println("result = ${result.second}")
                localPath = Serializer.save(urlInfo, it, result.second) // save with extension
            }
        }
        return localPath
    }
}

class CssContent(override val urlInfo: URLInfo) : AbstractContent(urlInfo) {
     override fun execute(): String {
         //println("Download CSS: ${urlInfo.getURL()} ...")

         var localPath = ""
         Downloader.download((urlInfo.getURL())).also { result ->
             result.first?.let {
                 localPath = Serializer.save(urlInfo, it, result.second) // save with extension
             }
         }
         return localPath
     }
 }

class JsContent(override val urlInfo: URLInfo) : AbstractContent(urlInfo) {
    override fun execute(): String {
        //println("Download JS: ${urlInfo.getURL()} ...")

        var localPath = ""
        Downloader.download((urlInfo.getURL())).also { result ->
            result.first?.let {
                localPath = Serializer.save(urlInfo, it, result.second) // save with extension
            }
        }
        return localPath
    }
}