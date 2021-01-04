# Develop Environment
## version

* Spring Boot : 2.4.0
* Spring DependencyManagement : 1.0.10.RELEASE
* Java : 11
* Gradle : gradle-6.6.1-all

## dependencies 
* Spring Web
* junit
* log4j2
* JPA
* lombok
* h2database

# Shot Cut
* Create Unit Test : Ctrl + Shift + T

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

