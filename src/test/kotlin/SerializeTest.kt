import fd.ContentType
import fd.Serializer
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializeTest {
    @Test
    fun TestCase01() {
        //Serializer.mkdirs("t/news-topics/images/tpc/2025/3/6/5f3643b8c3130349d60acd921bf66170134a8a0f5e303975a4d7ad531bc20643.jpg?h=200&w=200&pri=l.jpg")
        Serializer.createLocalPath("t/news-topics/images/tpc/2025/3/6/5f3643b8c3130349d60acd921bf66170134a8a0f5e303975a4d7ad531bc20643.jpg?h=200&w=200&pri=l", ContentType.JPG)
        assertEquals(1, 1)
    }

}