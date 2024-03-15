package app.pharmacy.api.kakao.service;

import app.pharmacy.AbstractIntegrationContainerBaseTest;
import app.pharmacy.api.kakao.dto.DocumentDto;
import app.pharmacy.api.kakao.dto.KakaoApiResponseDto;
import app.pharmacy.api.kakao.dto.MetaDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("API 요청 Retry 테스트")
class KakaoAddressSearchServiceRetryTest extends AbstractIntegrationContainerBaseTest {

	private final ObjectMapper mapper = new ObjectMapper();
	private final String address = "서울특별시 강남구 역삼동 736-1";
	@Autowired
	private KakaoAddressSearchService kakaoAddressSearchService;
	@MockBean
	private KakaoUriBuilderService kakaoUriBuilderService;
	private MockWebServer mockWebServer;

	//목 서버

	@BeforeEach
	void setUp() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();
		System.out.println("mockWebServer.getPort() = " + mockWebServer.getPort());
		System.out.println("mockWebServer.url(\"/\") = " + mockWebServer.url("/"));
	}

	@AfterEach
	void tearDown() throws IOException {
		mockWebServer.shutdown();
	}

	@Test
	@DisplayName("Kakao Map API 호출할 수 있다.")
	void retry_test_1() throws Exception {

		//given
		var metaDto = new MetaDto(200);
		var documentDto = DocumentDto.builder().addressName(address).build();
		var apiResponse = new KakaoApiResponseDto(metaDto, List.of(documentDto));
		URI uri = mockWebServer.url("/").uri();

		//when
		mockWebServer.enqueue(new MockResponse().setResponseCode(504)); // 1번째 요청:: 500 응답 발생
		mockWebServer.enqueue(new MockResponse().setResponseCode(200)
			.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.setBody(mapper.writeValueAsString(apiResponse))); // 2번째 요청:: 200 응답 발생

		when(kakaoUriBuilderService.buildAddressSearchUri(address)).thenReturn(uri);
		var apiResult = kakaoAddressSearchService.requestAddressSearch(address);

		//then
		assertEquals(2, mockWebServer.getRequestCount()); // 2번의 요청이 발생했는지 확인
		assertEquals(address, apiResult.getDocuments().get(0).getAddressName());
		assertEquals(200, apiResult.getMeta().getTotalCount());
	}

	@Test
	@DisplayName("Kakao Map API 호출시 최대 2번 모두 예외가 발생할 경우 요청처리가 실패한다.")
	void retry_test_2() {

		//given
		URI uri = mockWebServer.url("/").uri();


		//when
		mockWebServer.enqueue(new MockResponse().setResponseCode(504)); // 1번째 요청:: 500 응답 발생
		mockWebServer.enqueue(new MockResponse().setResponseCode(504)); // 2번째 요청:: 500 응답 발생

		when(kakaoUriBuilderService.buildAddressSearchUri(address)).thenReturn(uri);
		var apiResult = kakaoAddressSearchService.requestAddressSearch(address);

		//then
		System.out.println("apiResult = " + apiResult);
		assertEquals(2, mockWebServer.getRequestCount()); // 2번의 요청이 발생했는지 확인
		assertEquals(apiResult, KakaoApiResponseDto.empty());

	}

}
