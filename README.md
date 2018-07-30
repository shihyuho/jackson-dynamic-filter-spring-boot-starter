[![Build Status](https://travis-ci.org/shihyuho/jackson-dynamic-filter.svg?branch=master)](https://travis-ci.org/shihyuho/jackson-dynamic-filter-spring-boot-starter
)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.shihyuho/jackson-dynamic-filter-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.shihyuho/jackson-dynamic-filter-spring-boot-starter)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/shihyuho/jackson-dynamic-filter-spring-boot-starter/blob/master/LICENSE)

# Jackson Dynamic Property Filter - Spring Boot Starter

Spring Boot Starter for [jackson-dynamic-filter](https://github.com/shihyuho/jackson-dynamic-filter), which provides an easy way to determine filters dynamically with [jackson](https://github.com/FasterXML/jackson) WITHOUT writing annotations directly on java object, and it also well integration with Spring MVC/Spring Boot.

## Prerequisites

- Java 8 and above
- [Maven](http://maven.apache.org/) 3.0 and above
- Dependency versions controlled by Spring IO Platform: [Athens-SR1](https://docs.spring.io/platform/docs/Athens-SR1/reference/htmlsingle/#appendix-dependency-versions)


## Setup

All you need to do is to add dependency in your  Spring Boot Maven project:

```xml
<dependency>
    <groupId>com.github.shihyuho</groupId>
    <artifactId>jackson-dynamic-filter-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

Or in your Spring Boot Gradle project:

````groovy
compile 'com.github.shihyuho:jackson-dynamic-filter-spring-boot-starter:1.0.0'
````

and it's done, have fun!

## Using annotation

- `@SerializeAllExcept` - Serializing all properties, except for ones explicitly listed to be filtered out.
- `@FilterOutAllExcept` - Filtering out unknown properties and only serializes ones explicitly listed.

```java
@RestController
public class SomeController {

  @SerializeAllExcept({"someField", "anotherField"})
  @RequestMapping(value = "/without/some-fields", method = RequestMethod.GET)
  public SomeObject withoutSomeFields() {
    return someObject;
  }

  @FilterOutAllExcept({"someField", "anotherField"})
  @RequestMapping(value = "/only/some-fields", method = RequestMethod.GET)
  public SomeObject onlySomeFields() {
    return someObject;
  }
}
```

## Custom annotation

Define a custom annotation:

```java
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithoutAuditingFields {
}
```

then register a resolver, extends `DynamicFilterResolver`, as Spring bean. Starter will auto find out all `DynamicFilterResolver` (including subclasses) in Spring context.

```java
@Component
public class WithoutAuditingFieldsResolver extends DynamicFilterResolver<WithoutAuditingFields> {

  @Override
  public PropertyFilter apply(WithoutAuditingFields annotation) {
    return SimpleBeanPropertyFilter.serializeAllExcept(
        "id", "createdBy", "createdTime", "modifiedBy", "modifiedTime");
  }
}
```

> More detail about [SimpleBeanPropertyFilter](https://fasterxml.github.io/jackson-databind/javadoc/2.3.0/com/fasterxml/jackson/databind/ser/impl/SimpleBeanPropertyFilter.html)

then use it for you controller as follows:

```java
@RestController
public class SomeController {

  @WithoutAuditingFields
  @GetMapping(value = "/some-path")
  public SomeObject getSomeObject() {
    return someObject;
  }
}
```

## Application properties

The following properties can be specified inside your `application.properties` file, inside your `application.yml` file, or as command line switches. 

```properties
# Application will halt if any exception occurred while initialing jackson-dynamic-filter; otherwise just write a error log.
jackson.dynamic.filter.fail-fast=false 

# Resolver classes (with a default constructor) that are not Spring beans, but still need to inject into jackson-dynamic-filter
jackson.dynamic.filter.resolver.class-names=this.is.my.Resolver,yet.another.Resolver
```

## Code Examples

Actual Spring Boot configurations and examples can be found in the [./_examples/](tree/master/_examples)` directory.