<h1 align="center">Welcome to DomainCrawler 👋</h1>
<p>
  <a href="#" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
</p>

> Simple crawler limited to one domain and producing as output xml document with links categorized into: internal, external and static content.
> Given a page to crawl it should produce result in xml format like in this example:
```xml
<?xml version='1.0' encoding='UTF-8'?>
<CrawlResults>
  <baseUrl>http://example.com/</baseUrl>
  <pages>
    <page>
      <rootAddress>http://example.com/</rootAddress>
      <internalLinks/>
      <externalLinks>
        <externalLink>https://www.iana.org/domains/example</externalLink>
      </externalLinks>
      <staticContentLinks/>
    </page>
  </pages>
</CrawlResults>

```

## Install

```sh
./gradlew build
```

Or build a docker image:
```sh
docker build -t sbogutyn/crawler .
```

## Usage

```sh
./gradlew run
```

Or using docker:

```sh
docker run --rm -it sbogutyn/crawler
```

## Run tests

```sh
./gradlew test
```

## TODO

- [ ] Classifying links with static content

## Ideas for future extension

- [ ] Provide custom handlers for different types of content
- [ ] Improve performance by making multithreaded implementation
- [ ] Storing links in external databases or files
- [ ] Pause and resume crawl from last processed link
- [ ] Scale by using external queue and multiple workers crawling
- [ ] Providing multiple starting links from different domains
- [ ] Index results in full text search engine like elastic-search
- [ ] Handling dynamic pages by using WebDriver

## Author

👤 **Sławomir Bogutyn**

* Github: [@sbogutyn](https://github.com/sbogutyn)

## Show your support

Give a ⭐️ if this project helped you!

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_