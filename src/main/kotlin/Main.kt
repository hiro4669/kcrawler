package fd

fun main() {
    println("Welcome to KCrawler")
    //val topUrl = URLInfo("https", "www.yahoo.co.jp", "")
    //https://kids.yahoo.co.jp/
    //val topUrl = URLInfo("https", "kids.yahoo.co.jp", "")

    // https://www.lycorp.co.jp/ja/company/athletes/
    //val topUrl = URLInfo("https", "www.lycorp.co.jp", "ja/company/athletes/")

    // https://shopping.yahoo.co.jp/?sc_e=ytc
    //val topUrl = URLInfo("https", "shopping.yahoo.co.jp", "?sc_e=ytc")

    // https://auctions.yahoo.co.jp/
    val topUrl = URLInfo("https", "auctions.yahoo.co.jp", "") // https://auction-assets.c.yimg.jp/top/2.278.0/pc_renewal_MyBox_icon_my.svgがとれない．謎

    HtmlContent(topUrl, 0).execute()
}