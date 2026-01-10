# Jackson DataType Hibernate

This project uses Jackson's and Hibernate's public API filter out 
uninitialized lazy properties.
Since this is using public API's, It should work any Hibernate versions.

## Usage in spring-boot

Just include the dependency. It contains spring-boot configuration class 
that should enough to configure spring managed ObjectMapper.
```xml
<dependency>
    <groupId>io.github.hurelhuyag</groupId>
    <artifactId>jackson-datatype-hibernate</artifactId>
    <version>0.0.0</version>
</dependency>
```

For spring-boot 4.0
```xml
<dependency>
    <groupId>io.github.hurelhuyag</groupId>
    <artifactId>jackson-datatype-hibernate</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Contribution

It is Apache 2.0 Licensed open source project. If you anything in you mind to improve this project. Feel free to file an issue or open pull request.

## 

[![Buy Me a Coffee](https://img.shields.io/badge/Buy%20Me%20a%20Coffee-FFDD00?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black)](https://www.buymeacoffee.com/hurelhuyag)
