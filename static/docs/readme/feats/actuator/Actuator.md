[Back](../FeatIndex.md)

# Spring Boot Actuator
* 기능 설명
    * Spring Boot에서 자체적으로 제공해주는 어플리케이션을 모니터링하고 관리하는 기능
* Gradle 사용법
    ```
    dependencies {
        compile("org.springframework.boot:spring-boot-starter-actuator")
    }
    ```
* Maven 사용법
    ```
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>
    ```
* yml 설정
    * 전체 endpoint 활성화
    ```
    management:
        endpoints:
          web:
            exposure:
              include: "*"
    ```
* end points
    ```
    | ID               | Description                                                                                                                                                                 | Enabled by default |
    |------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
    | auditevents      | Exposes audit events information for the current application.                                                                                                               | Yes                |
    | beans            | Displays a complete list of all the Spring beans in your application.                                                                                                       | Yes                |
    | caches           | Exposes available caches.                                                                                                                                                   | Yes                |
    | conditions       | Shows the conditions that were evaluated on configuration and auto-configuration classes and the reasons why they did or did not match.                                     | Yes                |
    | configprops      | Displays a collated list of all @ConfigurationProperties.                                                                                                                   | Yes                |
    | env              | Exposes properties from Spring’s ConfigurableEnvironment.                                                                                                                   | Yes                |
    | flyway           | Shows any Flyway database migrations that have been applied.                                                                                                                | Yes                |
    | health           | Shows application health information.                                                                                                                                       | Yes                |
    | httptrace        | Displays HTTP trace information (by default, the last 100 HTTP request-response exchanges).                                                                                 | Yes                |
    | info             | Displays arbitrary application info.                                                                                                                                        | Yes                |
    | integrationgraph | Shows the Spring Integration graph.                                                                                                                                         | Yes                |
    | loggers          | Shows and modifies the configuration of loggers in the application.                                                                                                         | Yes                |
    | liquibase        | Shows any Liquibase database migrations that have been applied.                                                                                                             | Yes                |
    | metrics          | Shows ‘metrics’ information for the current application.                                                                                                                    | Yes                |
    | mappings         | Displays a collated list of all @RequestMapping paths.                                                                                                                      | Yes                |
    | scheduledtasks   | Displays the scheduled tasks in your application.                                                                                                                           | Yes                |
    | sessions         | Allows retrieval and deletion of user sessions from a Spring Session-backed session store. Not available when using Spring Session’s support for reactive web applications. | Yes                |
    | shutdown         | Lets the application be gracefully shutdown.                                                                                                                                | No                 |
    | threaddump       | Performs a thread dump.                                                                                                                                                     | Yes                |
    ```
    * [자세한 정보](https://docs.spring.io/spring-boot/docs/2.1.8.RELEASE/reference/html/production-ready-endpoints.html)
      
* 예제 : https://127.0.0.1:8881/actuator
* 출처 : https://docs.spring.io/spring-boot/docs/2.1.8.RELEASE/reference/html/production-ready-enabling.html
