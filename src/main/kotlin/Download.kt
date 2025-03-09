package fd

import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI


enum class ContentType {
    JPG,
    PNG,
    HTML,
    CSS,
    JS,
    OTHER
}

object Downloader {
    private val buffer: ByteArray = ByteArray(256)

    init {
        println("Init Downloader")
    }

    fun getContentType(ctype: String?) : ContentType {
        return when (ctype) {
            "image/jpeg" -> ContentType.JPG
            "image/png" -> ContentType.PNG
            "text/html" -> ContentType.HTML
            "text/html; charset=UTF-8" -> ContentType.HTML
            "text/css" -> ContentType.CSS
            "application/javascript" -> ContentType.JS
            "text/javascript" -> ContentType.JS
            else -> {
                throw RuntimeException("unknown type $ctype")
            }
        }
    }

    fun download(absoluateURL: String): Pair<ByteArray?, ContentType> {
        println("Download $absoluateURL . . .")
        val url = URI.create(absoluateURL).toURL()
        //val inStream = url.openConnection().getInputStream()
        var ctype: String? = null
        val inStream = url.openConnection().apply {
            setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3764.0 Safari/537.36")
            ctype = contentType
        }.getInputStream()

        println("contentType = $ctype")
        val rawContents = inStream?.let {
            val bout = ByteArrayOutputStream()
            while (true) {
                val len = it.read(buffer, 0, buffer.size)
                if (len == -1) break
                bout.write(buffer, 0, len)
            }
            bout.toByteArray()
        }
        return rawContents to getContentType(ctype)
    }

    fun downloadf(fileName: String): ByteArray? {
        val html = File(fileName).inputStream().let {
            val buffer = ByteArray(1024)
            val bout = ByteArrayOutputStream()
            while (true) {
                val len = it.read(buffer, 0, buffer.size)
                if (len == -1) break
                bout.write(buffer, 0, len)
            }
            bout.toByteArray()
        }
        return html
    }
}