package fd

class URLInfo(val protocol: String, val domain: String, var path: String) {
    var embed = false
    fun getURL(): String {
        return "$protocol://$domain/$path".trim()
    }
}


object Converter {
    init {
        println("Init Converter")
    }

    fun getAbsoluatePath(parentPath: String, childPath: String): String {
        val parentPathList = parentPath.split("/")
        val childPathList = childPath.split("/")
        val absoluteList = parentPathList.toMutableList().also {it.removeLast() }
        childPathList.forEach { elem ->
            when(elem) {
                "." -> {}
                ".." -> {
                    absoluteList.removeLast()
                }
                else -> {
                    absoluteList.addLast(elem)
                }
            }
        }
        return absoluteList.joinToString("/")
    }

    fun convert(url: String, parentUrl: URLInfo): URLInfo {
        lateinit var protocol: String
        lateinit var domain: String
        lateinit var path: String
        var _embed = false

        if (url.startsWith("http")) {
            val elements = url.split("://")
            protocol = elements[0]
            domain = elements[1].also {element ->
                path = element.substringAfter("/", "")
            }.substringBefore("/")
        } else if (url.startsWith("//")) {
            protocol = parentUrl.protocol
            domain = url.substring(2).also { element ->
                path = element.substringAfter("/", "")
            }.substringBefore("/")
        } else if (url.startsWith("/")) {
            protocol = parentUrl.protocol
            domain = parentUrl.domain
            path = url.substringAfter("/")
        } else if (url.startsWith("./") || url.startsWith("../")) {
            protocol = parentUrl.protocol
            domain = parentUrl.domain
            path = getAbsoluatePath(parentUrl.path, url)
        } else if (url.startsWith("data:image")) {
            protocol = parentUrl.protocol
            domain = parentUrl.domain
            path = url
            _embed = true
        } else {
            println("what else? url = $url")
        }

        return URLInfo(protocol, domain, path).apply { embed = _embed }
    }
}