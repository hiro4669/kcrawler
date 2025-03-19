package fd

fun main() {
    println("Welcome to KCrawler")
    //https://kids.yahoo.co.jp/
    val topUrl = URLInfo("https", "www.yahoo.co.jp", "")
    //val topUrl = URLInfo("https", "kids.yahoo.co.jp", "")
    HtmlContent(topUrl, 0).execute()
}