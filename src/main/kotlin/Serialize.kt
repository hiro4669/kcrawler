package fd

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths

object Serializer {
    val basedir = "output"
    init {
        println("Init Serializer")
    }

    private fun mkdirs(path: String): String {
        val dirName = "$basedir/${path.substringBeforeLast("/")}"
        Files.createDirectories(Paths.get(dirName))
        return dirName
    }

    fun createLocalPath(path: String, ctype: ContentType = ContentType.OTHER): String {
        return mkdirs(path).let {
            //println("path = $path")
            val fileName = path.substringAfterLast("/").let {_fname ->
                    "$_fname${getExtension(ctype)}"
            }
            //println("fileName = $fileName")
            //println("$it/$fileName")
            "$it/$fileName"
        }
    }

    fun save(path: String, data: ByteArray, ctype: ContentType): String {
        val ofile = File(createLocalPath(path, ctype))
        BufferedOutputStream(FileOutputStream(ofile)).use {
            it.write(data)
            it.flush()
        }
        return ofile.absolutePath
    }

    private fun getExtension(ctype: ContentType): String {
        return when (ctype) {
            ContentType.JPG -> ".jpg"
            ContentType.HTML -> ".html"
            ContentType.CSS -> ".css"
            ContentType.JS -> ".js"
            ContentType.PNG -> ".png"
            else -> ""
        }
    }
}