<h1 align="center">Welcome to DomainCrawler 👋</h1>
<p>
  <a href="#" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
</p>

> Simple crawler limited to one domain and producing as output xml document with links categorized into: internal, external and static content.

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

## Author

👤 **Sławomir Bogutyn**

* Github: [@sbogutyn](https://github.com/sbogutyn)

## Show your support

Give a ⭐️ if this project helped you!

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_