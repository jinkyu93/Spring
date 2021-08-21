[Back](../FeatIndex.md)

# DbUnit

## 개요
* Unit test 시 Database 연동이 필요한 경우 사용할 수 있는 library
* 공식 라이브러리가 아니며, 버전업데이트가 멈췄다

## 기능
* xml 파일을 통한 DB 상태 initialize 기능
    * 매 테스트마다 같은 데이터로 독립적인 테스트 가능

## Annotations
* @DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
    * DbUnit 을 사용하기 위한 설정 class 를 반환하는 method 등록
        * return type : DatabaseDataSourceConnectionFactoryBean
        * class 지정 필요
            * ex) @Import(DbUnitConfig.class)
* @DatabaseSetup
    * 각 test 실행 전 동작 지정
* @DatabaseTearDown(
    * 각 test 실행 후 동작 지정
* @TestExecutionListeners(
    * DbUnitTestExecutionListener.class
    * DependencyInjectionTestExecutionListener.class : bean 을 di 받기 위해서 필요
    * mergeMode
        * TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS 로 사용
        * default 값인 REPLACE_DEFAULTS 의 경우 spring-boot-test 에서 기본적으로 loading 해주는 TestExecutionListeners 들이 무시된다. 
            * ex) @MockBean