import fd.ContentType
import fd.Serializer
import fd.URLInfo
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializeTest {
    @Test
    fun TestCase01() {
        val urlInfo = URLInfo("https", "www.yahoo.co.jp", "")
        val data = "Hoge123".encodeToByteArray()
        val localPath = Serializer.save(urlInfo, data, ContentType.HTML)
        println("localPath : $localPath")
        val test = "8c45387a4db38d18aeac25e93080c36c6cc3bb946d3ccda34d0514b6e18bf5bc.jpg?h=200&w=200&pri=l&fmt=webp"
        //val test = "hoge123"
        val retv = if (test.length > 50) test.substring(0, 50) else test

        println(retv)

        assertEquals(1, 1)
    }


}