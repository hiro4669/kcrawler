package fd

fun main() {
    println("Welcome to KCrawler")
    val topUrl = URLInfo("https", "www.yahoo.co.jp", "")
    //https://kids.yahoo.co.jp/
    //val topUrl = URLInfo("https", "kids.yahoo.co.jp", "")

    // https://www.lycorp.co.jp/ja/company/athletes/
    //val topUrl = URLInfo("https", "www.lycorp.co.jp", "ja/company/athletes/")
    HtmlContent(topUrl, 0).execute()
}