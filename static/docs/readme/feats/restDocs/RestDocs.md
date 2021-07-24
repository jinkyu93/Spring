[Back](../FeatIndex.md)

# Rest Docs
## 개요
* 테스트 기반으로 api 문서를 만들기 위함
* 테스트를 통과해야 api 문서가 만들어진다
## 사용 방법
* 작업 순서
    * Unit Test 수정
        * @AutoConfigureRestDocs
        * document(identifierPath)
    * Unit Test 실행 시 build/generated-snippets/ 하위에 snippet 파일 자동 생성
    * snippet 파일을 사용해서 src/docs/asciidoc/ 하위에 adoc template 직접 생성
        * maven 은 src/main/asciidoc/
    * gradlew build 를 사용해서 static/docs/api/ 하위에 html 파일 자동 생성
* 접속 예제 : http://127.0.0.1:8881/docs/api/account.html
