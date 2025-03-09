import fd.Converter
import fd.URLInfo
import kotlin.test.Test
import kotlin.test.assertEquals

class ConvertTest {
    val rootURL = URLInfo("https", "www.yahoo.co.jp", "")

    @Test
    fun TestCase01() {
        val url = "https://www.yahoo.co.jp/sample/desuyo/hoge.html"
        var urlInfo = Converter.convert(url, rootURL)
        val relativeUrl = "../../relative.html"
        urlInfo = Converter.convert(relativeUrl, urlInfo)
        assertEquals("https://www.yahoo.co.jp/relative.html", urlInfo.getURL())
    }

}