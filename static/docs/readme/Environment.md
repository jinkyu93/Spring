# Develop Environment
## version

```
Spring Boot : 2.4.0
Spring DependencyManagement : 1.0.10.RELEASE
Java : 11
Gradle : gradle-6.6.1-all
```

## dependencies 
* Spring Web
* junit
* log4j2
* JPA
* lombok
* h2database
* Jackson

# Shortcut
* Create Unit Test : Ctrl + Shift + T
* Move Line Up or Down : Shift + Alt + Up or Down
* Move Cursor Before After : Alt + Ctl + left or right
* Auto Line Alignment : Alt + Ctl + L

# Terms
* Domain : Entity
    * DB 와 매칭될 객체
* Repository : DAO(Data Access Object)
    * DB 에 접근하는 객체
* DTO : DTO(Data Transfer Object) or VO(Value Object)
    * 계층간 데이터 교환을 위한 객체
    * Setter / Getter 만을 가지는 순수한 모델
        * 예외로 toEntity 정도는 가질 수 있는 듯
    * Entity <-> DAO <-> Service <-> Controller <-> Client 사이에서 사용
    * DTO vs VO
        * 동일하지만 VO 는 readonly 속성을 가진다
        
# Annotations
* @Autowired
* @AllArgsConstructor
* @Slf4j
* @Transactional : 테스트 실행 후 테스트 결과를 Rollback 시키고 싶은 경우

# Features

## UnitTest
* 
### DbUnit
* 

## Rest Docs
* 작업 순서
    * Unit Test 수정
        * @AutoConfigureRestDocs
        * document(identifierPath)
    * Unit Test 실행 시 build/generated-snippets/ 하위에 snippet 파일 자동 생성
    * snippet 파일을 사용해서 src/docs/asciidoc/ 하위에 adoc template 직접 생성
        * maven 은 src/main/asciidoc/
    * gradlew build 를 사용해서 static/docs/api/ 하위에 html 파일 자동 생성
* 예제 : http://127.0.0.1:8881/docs/api/account.html

## Spring Security
* SSL
    * 인증서 생성
        * jdk bin 폴더의 keytool 사용
        * .\keytool -genkey -alias cert -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore cert.p12 -validity 36500
            * genkey : 키를 생성하겠음
            * alias : 키의 별칭
            * storetype : 저장 타입
        * sample information
            * Generating 2,048 bit RSA key pair and self-signed certificate (SHA256withRSA) with a validity of 36,500 days
            * for: CN=park, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown, Password=password
