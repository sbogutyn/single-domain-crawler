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
> This project requires jdk 12 for building. Jdk 13 doesn't work because it's not 
> supported by current gradle version. I've added docker image based on openjdk-12 that 
> is able to both build and run this project.

```sh
./gradlew build
```

> Or build a docker image:
```sh
docker build -t sbogutyn/crawler .
```

## Usage

> Crawler accepts following command line arguments:

```text
Usage: <main class> [options]
  Options:
    --crawlLimit, -l
      Maximum number of links to crawl.
    --help, -h

    --startingUrl, -s
      Base url to start the crawling process.
      Default: http://example.com
```

> Can be start directly by running Main class, using gradle by:

```sh
./gradlew run --args="-l 10 -s http://example.com"
```

> Or using docker:

```sh
docker run --rm sbogutyn/crawler -l 10 -s http://example.com
```

## Run tests

```sh
./gradlew test
```

## Reasoning 
> This implementation uses Jsoup for both http client and parsing html.
> After given page is parsed, all links are extracted and classified into 3 categories:
> internal, external and static content.
> Results are kept in memory. For this I've used set of already processed links
> to ensure no duplicates and queue for pending links. Only internal links 
> are added to queue.
> Crawling can continue until all links in given domain are processed (pending queue is empty)
> or until we reach limit of crawled links which can be provided as optional command line argument.
> Finally xml document representing all crawled pages is produced using jackson-databind-xml.
> All pages are on the same level without nesting between parent/child pages.

# Trade offs
> It is single threaded as solution aimed to be simple, but performance could be greatly improved by
> making it parallel. 
> Handling of static content links is very basic due to time constraints on implementation,
> could be improved by using content type of response. 
> Because it is using simple http client and html parser it doesn't work with dynamic pages created mostly,
> with javascript. For this kind of pages it would be better to use WebDriver implementation, but that would 
> make crawling process slower.
> In current form it is also not scalable, that could be achieved by using external queue and distributing 
> links to multiple workers but that also introduces more complexity. 


## Ideas for future extension

- [ ] Provide custom handlers for different types of content
- [ ] Improve performance by making multithreaded implementation
- [ ] Storing links in external databases or files
- [ ] Pause and resume crawl from last processed link
- [ ] Scale by using external queue and multiple workers crawling
- [ ] Providing multiple starting links from different domains
- [ ] Index results in full text search engine like elastic-search
- [ ] Handling dynamic pages by using WebDriver
- [ ] Adding external configuration (for setting timeouts etc.)
- [ ] Improve classification by handling different Content-Types instead of relying on extension
- [ ] Handling sub domains 

## Author

👤 **Sławomir Bogutyn**

* Github: [@sbogutyn](https://github.com/sbogutyn)

## Show your support

Give a ⭐️ if this project helped you!

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_