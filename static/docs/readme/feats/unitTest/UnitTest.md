[Back](../FeatIndex.md)

# Unit Test

## 개요
* 하나의 작은 기능에 대한 테스트 코드를 의미
* 1기능 1테스트를 원칙으로 코드를 작성할 것
* 해당 테스트가 쌓여서 코드의 수정이나, 리펙토링 시 버그를 확인할 수 있게 해준다.

## Annotations
* @SpringBootTest
* @AutoConfigureMockMvc
* @WebMvcTest
    * 웹과 관련된 Bean 만 주입
	    * ex) @Controller
	* @Component 관련 Bean 은 주입되지 않기 때문에 Test 시에는 @MockBean 으로 가짜 객체를 주입시켜 줘야한다
	    * ex) Service, Repository
	* 전수테스트가 아닌 유닛테스트 용도
	* Service 가 아닌 Controller 와의 데이터 통신만을 테스트 하기 위한 Unit Test Model
* @MockBean
    * @MockBean 내부는 텅 빈 Service 객체를 가져서 실제와 같이 동작을 하지 않는다
    * 어떤 동작을 호출 했을 때, 어떤 결과를 return 할 지 임의로 설정 가능

## Usage
```
    @Test
    public void getUserNotFound() throws Exception {
        // given
        RequestBuilder builder = get(urlTemplate).param(testIdKey, testIdValue);
        when(service.findById(testIdValue))
                .thenThrow(new AccountNotFoundException());

        // when
        var resultActions = mockMvc.perform(builder);

        // then
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andDo(document("account/get/failure"));
    }
```