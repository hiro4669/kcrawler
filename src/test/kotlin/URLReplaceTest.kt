
import fd.HtmlContent
import fd.URLInfo
import kotlin.test.Test
import kotlin.test.assertEquals

class URLReplaceTest {

    @Test
    fun TestCase01() {
        val urlInfo = URLInfo("https", "www.yahoo.co.jp", "")
        val htmlContent = HtmlContent(urlInfo)
        val ourl = " https://s.yimg.jp/images/ds/managed/1/managed-ual.min.js?tk=4465a92c-f0fd-406f-b519-efd409cc9849&service=toppage"
        val url = " https://s.yimg.jp/images/ds/managed/1/managed-ual.min.js?tk=4465a92c-f0fd-406f-b519-efd409cc9849&amp;service=toppage"
        assertEquals(url, htmlContent.adjust(ourl))
    }
}