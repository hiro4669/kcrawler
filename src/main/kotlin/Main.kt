package fd

fun main() {
    println("Hello World!")
    val topUrl = URLInfo("https", "www.yahoo.co.jp", "")
    HtmlContent(topUrl, 0).execute()
}