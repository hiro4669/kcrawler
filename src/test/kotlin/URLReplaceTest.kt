
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


    @Test
    fun TestCase02() {
        val urlInfo = URLInfo("https", "www.yahoo.co.jp", "")
        val htmlContent = HtmlContent(urlInfo)
        val ourl = "/PccHelpcenter/s/sfsites/l/%7B%22mode%22%3A%22PROD%22%2C%22dfs%22%3A%228%22%2C%22app%22%3A%22siteforce%3AcommunityApp%22%2C%22fwuid%22%3A%22c1ItM3NYNWFUOE5oQkUwZk1sYW1vQWg5TGxiTHU3MEQ5RnBMM0VzVXc1cmcxMS4zMjc2OC4z%22%2C%22loaded%22%3A%7B%22APPLICATION%40markup%3A%2F%2Fsiteforce%3AcommunityApp%22%3A%221233_vZx87dHGHIhS0MXRTe4D5w%22%7D%2C%22apce%22%3A1%2C%22apck%22%3A%22JHt0aW1lc3RhbXB9MDAwMDAwMDAwMDBqYQ%22%2C%22mlr%22%3A1%2C%22pathPrefix%22%3A%22%2FPccHelpcenter%22%2C%22dns%22%3A%22c%22%2C%22ls%22%3A1%2C%22lrmc%22%3A%22-386269907%22%7D/resources.js?pu=1&pv=1742398833000-1222576573&rv=1742370169000"
        val url  = "/PccHelpcenter/s/sfsites/l/%7B%22mode%22%3A%22PROD%22%2C%22dfs%22%3A%228%22%2C%22app%22%3A%22siteforce%3AcommunityApp%22%2C%22fwuid%22%3A%22c1ItM3NYNWFUOE5oQkUwZk1sYW1vQWg5TGxiTHU3MEQ5RnBMM0VzVXc1cmcxMS4zMjc2OC4z%22%2C%22loaded%22%3A%7B%22APPLICATION%40markup%3A%2F%2Fsiteforce%3AcommunityApp%22%3A%221233_vZx87dHGHIhS0MXRTe4D5w%22%7D%2C%22apce%22%3A1%2C%22apck%22%3A%22JHt0aW1lc3RhbXB9MDAwMDAwMDAwMDBqYQ%22%2C%22mlr%22%3A1%2C%22pathPrefix%22%3A%22%2FPccHelpcenter%22%2C%22dns%22%3A%22c%22%2C%22ls%22%3A1%2C%22lrmc%22%3A%22-386269907%22%7D/resources.js?pu=1&amp;pv=1742398833000-1222576573&amp;rv=1742370169000"
        assertEquals(url, htmlContent.adjust(ourl))

    }
}