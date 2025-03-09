# Kcrawler - Tiny HTML Crawler by Kotlin

Kcrawler is a tiny and lightweight Kotlin HTML crawler that uses Ksoup, HTML parser for Kotlin, downloading HTML and extracting resoruces such as JS, CSS and Images and save everything on the local environment.

## Feature

Kcrawler downloads HTML, CSS, JS and Images and save them, however it doesn't execute JS on the fly and analyze CSS.

## Usage
Create `URLInfo` object and pass it to `HTMLContent` and invoke `execute`

```kotlin
val topUrl = URLInfo("https", "www.yahoo.co.jp", "")
HtmlContent(topUrl).execute()
```

## Note
This project assumes to be used Intellij. Therefore after downloading this project `git pull`, create a new Kotlin project specifying the downloaded directory.

## Licencse
None
