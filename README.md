Java Servlet Cookie Filter
==========================

This project provides a transparent Java `@WebFilter`, registering a thread local system-wide `CookieHandler` used by all standard HTTP protocol handler connections.

### Easy server-side cookie handling 
Handling user-based cookies on a server-side servlet application really proves difficult in Java. Cookies (at least for all standard protocol handler implementations) are handled by one central `CookieHandler` object, which can be set and retrieved using the `CookieHandler`'s `setDefault` and `getDefault` methods. As this cookie handler is used system-wide, each process shares the same cookies for any given URL. For servlets handling cookies, this becomes a problem sooner or later, e.g. if the servlet is dealing with user-specific logins, or similar.

With the *Java Servlet Cookie Filter* user-based cookie handling becomes a piece of cake. When loading the classes, the `CookieFilter` class registers as a transparent (no data gets changed) filter implementation using the `@WebFilter` annotation. The filter first registers a new default `CookieHandler` which then dynamically intercepts each request and response. From now on each user gets access to a dedicated `CookieStore`. The store is purged as soon as the response is sent, so the next request going to the same thread will again receive a new `CookieStore`. This eliminates the risk of sharing any cookie information between threads or requests.

Usage
-----

Good news, just include the `lc.kra.servlet.filter.CookieFilter` and `lc.kra.servlet.filter.helper.ThreadLocalCookieStore` classes to your dynamic web project and you are done! This works for all servers implementing at least version 3.0 of the Java EE servlet specification. The filter is dynamically registered using the `@WebFilter` annotation, thus there is no need of changing your `web.xml` file.

### Maven Dependency
You can include cookie-filter from this GitHub repository by adding this dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>lc.kra.servlet.filter</groupId>
  <artifactId>cookie-filter</artifactId>
  <version>0.1.0</version>
</dependency>
```

Additionally you will have to add the following repository to your `pom.xml`:

```xml
<repositories>
  <repository>
    <id>cookie-filter-mvn-repo</id>
    <url>https://raw.github.com/kristian/cookie-filter/mvn-repo/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>
```

Build
-----

To build cookie-filter on your machine, checkout the repository, `cd` into it, and call:
```
mvn clean install
```

License
-------

The code is available under the terms of the [MIT License](http://opensource.org/licenses/MIT).