package fd

fun main() {
    println("Welcome to KCrawler")
    val topUrl = URLInfo("https", "www.yahoo.co.jp", "")

    //https://kids.yahoo.co.jp/
    //val topUrl = URLInfo("https", "kids.yahoo.co.jp", "")

    // https://www.lycorp.co.jp/ja/company/athletes/
    //val topUrl = URLInfo("https", "www.lycorp.co.jp", "ja/company/athletes/")

    // https://shopping.yahoo.co.jp/?sc_e=ytc
    //val topUrl = URLInfo("https", "shopping.yahoo.co.jp", "?sc_e=ytc")

    // https://auctions.yahoo.co.jp/
    //val topUrl = URLInfo("https", "auctions.yahoo.co.jp", "") // https://auction-assets.c.yimg.jp/top/2.278.0/pc_renewal_MyBox_icon_my.svgがとれない．謎

    // error https://support.yahoo-net.jp/PccHelpcenter/s/ -> OK
    //val topUrl = URLInfo("https", "support.yahoo-net.jp", "PccHelpcenter/s/")

    // error https://premium.yahoo.co.jp/?sc_e=stpage_lyp_mast_head_pc_prem -> OK embedded image
    //val topUrl = URLInfo("https", "premium.yahoo.co.jp", "?sc_e=stpage_lyp_mast_head_pc_prem")

    // unreachable? https://zozo.jp/?utm_source=yahoo&utm_medium=referral&utm_campaign=web_ytop-leftcolumn
    //val topUrl = URLInfo("https", "zozo.jp", "?utm_source=yahoo&utm_medium=referral&utm_campaign=web_ytop-leftcolumn")

    // https://transit.yahoo.co.jp/
    //val topUrl = URLInfo("https", "transit.yahoo.co.jp", "")

    // https://sports.yahoo.co.jp/,
    //val topUrl = URLInfo("https", "sports.yahoo.co.jp", "")

    // https://www.kurashiru.com/?utm_source=yahoo_top_contentbox
    //val topUrl = URLInfo("https", "www.kurashiru.com", "?utm_source=yahoo_top_contentbox")

    // https://baseball.yahoo.co.jp/hsb_spring/
    //val topUrl = URLInfo("https", "baseball.yahoo.co.jp", "hsb_spring/")

    // TODO japanese.webp
    // https://jp.stanby.com/?sr_fr=job_detail&utm_source=yjtop&utm_medium=leftcolumn&utm_campaign=yj_leftcolumn
    //val topUrl = URLInfo("https", "jp.stanby.com", "?sr_fr=job_detail&utm_source=yjtop&utm_medium=leftcolumn&utm_campaign=yj_leftcolumn")

    // https://www.paypay-corp.co.jp/store/campaign/
    // val topUrl = URLInfo("https", "www.paypay-corp.co.jp", "store/campaign/")

    HtmlContent(topUrl, 1).execute()
}