package fd

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths

object Serializer {
    val MAX_LEN = 50
    val MAX_DIR_LEN = 100
    val basedir = "output"
    val banlist = listOf("&", "?", "%")


    val base: File
    init {
        println("Init Serializer")
        base = File(basedir)
    }

    private fun mkdirs(path: String): String {
        var dirName = rename("$basedir/${path.substringBeforeLast("/")}")
        //println("dirName1 = $dirName")
        if (dirName.length > MAX_DIR_LEN) dirName = dirName.substring(0, MAX_DIR_LEN)
        //println("dirName2 = $dirName")
        Files.createDirectories(Paths.get(dirName))
        return dirName
    }

    private fun rename(oname: String) =
        banlist.fold(oname) {name, bchar -> name.replace(bchar, "")}

    fun createLocalPath(path: String, ctype: ContentType = ContentType.OTHER): String {
        return mkdirs(path).let {
            val fileName = path.substringAfterLast("/").let {
                //val _fname = if (it.length > MAX_LEN) it.substring(0, MAX_LEN) else it
                val _fname = if (it.length > MAX_LEN) it.substring(it.length - MAX_LEN) else it
                //println("fname  = $_fname")
                //println("rename = ${rename(_fname)}")
                "${rename(_fname)}${getExtension(ctype)}"

            }
            "$it/$fileName"
        }
    }

    private fun save(path: String, data: ByteArray, ctype: ContentType): String {
        val ofile = File(createLocalPath(path, ctype))
        BufferedOutputStream(FileOutputStream(ofile)).use {
            it.write(data)
            it.flush()
        }
        //println("relativePath = ${ofile.toRelativeString(base)}")
        return ofile.absolutePath
    }

    fun save(urlInfo: URLInfo, data: ByteArray, ctype: ContentType): String {
        //urlInfo.apply {if (path == "") path = "index.html" }

        urlInfo.apply {
            if (path == "") path = "index"
            else if (path.endsWith("/")) path += "index"
        }


        return save(urlInfo.domain + "/" + urlInfo.path, data, ctype)
    }

    private fun getExtension(ctype: ContentType): String {
        return when (ctype) {
            ContentType.JPG -> ".jpg"
            ContentType.HTML -> ".html"
            ContentType.CSS -> ".css"
            ContentType.JS -> ".js"
            ContentType.PNG -> ".png"
            ContentType.WEBP -> ".webp"
            ContentType.SVG -> ".svg"
            ContentType.GIF -> ".gif"
            ContentType.JSON -> ".json"
            ContentType.PLAIN -> ".txt"
            ContentType.BINARY -> ""
            else -> ""
        }
    }
}