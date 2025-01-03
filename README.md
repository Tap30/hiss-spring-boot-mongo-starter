# Hiss Spring Boot Mongo Starter [![build status](https://github.com/Tap30/hiss-spring-boot-mongo-starter/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/Tap30/hiss-spring-boot-mongo-starter/actions/workflows/build.yml)

An small library which uses Spring Boot autoconfiguration capability that integrates Hiss with Spring Boot and Spring Data Mongo.

By integrating Hiss with Spring Boot project we mean registration of:
- Hiss bean [using environment variables](https://github.com/Tap30/hiss?tab=readme-ov-file#creating-properties-from-environment-variables)
- Mongo interceptor which automatically encrypts objects before saving to DB and decrypts them after loading.

An example project is created [here](https://github.com/mkay1375/spring-with-hiss-example).

## Quick Start

### 1. Add Hiss dependency

Apache Maven:
```xml
<dependency>
    <groupId>io.github.tap30</groupId>
    <artifactId>hiss-spring-boot-mongo-starter</artifactId>
    <version>0.10.0</version>
</dependency>
```

Gradle (Groovy):
```groovy
implementation 'io.github.tap30:hiss-spring-boot-mongo-starter:0.10.0'
```

Gradle (Kotlin):
```kotlin
implementation("io.github.tap30:hiss-spring-boot-mongo-starter:0.10.0")
```

### 2. Set environment variables

```bash
HISS_KEYS_A=AAAAAAAAAAAAAAAAAAAAAA==
HISS_KEYS_B=AAAAAAAAAAAAAAAAAAAAAA==
# other keys...
HISS_DEFAULT_ENCRYPTION_KEY_ID=a
HISS_DEFAULT_ENCRYPTION_ALGORITHM=aes-128-gcm
HISS_DEFAULT_HASHING_KEY_ID=b
HISS_DEFAULT_HASHING_ALGORITHM=hmac-sha256
```

For more information about envs see
[this](https://github.com/Tap30/hiss?tab=readme-ov-file#creating-properties-from-environment-variables).

### 3. Annotate your class with `@Encrypted`

```java
import io.github.tap30.Encrypted;

public class User {
    @Encrypted
    private String phoneNumber;
    private String hashedPhoneNumber;

    // getters and setters
}
```

Note: Getters and setters must exist as Hiss use them to get/set values.

## Using custom `HissPropertiesProvider`

By implementing `HissPropertiesProvider` and annotating it with `@Component`
this library will pick your implementation rather than default one.

## Using custom encryption and hashing algorithms

Just like custom `HissPropertiesProvider` by implementing `Encryptor` and `Hasher` interfaces
and annotating them with `@Component`, this library will pick them up.

For more information about `Encryptor`s and `Hasher`s see [Hiss readme](https://github.com/Tap30/hiss?tab=readme-ov-file#supported-algorithms).

## Querying Data

Currently there is not an easy way to support querying encrypted fields.

To query data, inject Hiss bean (`@Autowired Hiss hiss`)
and use `Hiss$hash(String)` method to generate hash of content;
then pass it to the queries which use hashed fields.
