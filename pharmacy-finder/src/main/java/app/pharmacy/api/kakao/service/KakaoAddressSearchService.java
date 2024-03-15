package app.pharmacy.api.kakao.service;

import app.pharmacy.api.kakao.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoAddressSearchService {

	private final KakaoUriBuilderService kakaoUriBuilderService;
	private final RestTemplate restTemplate;

	@Value("${kakao.rest.api.key}")
	private String kakaoRestApiKey;

	@Value("${kakao.rest.api.prefix}")
	private String kakaoRestApiPrefix;

	@Retryable(
		value = {RuntimeException.class}, // 재시도할 Exception 종류
		maxAttempts = 2, // 최대 재시도 횟수
		backoff = @Backoff(delay = 2000) // 재시도 간격 2초
	)
	public KakaoApiResponseDto requestAddressSearch(String address) {
		if (Objects.isNull(address)) {
			return null;
		}

		URI uri = kakaoUriBuilderService.buildAddressSearchUri(address);

		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, kakaoRestApiPrefix + " " + kakaoRestApiKey);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		return restTemplate
			.exchange(uri, GET, entity, KakaoApiResponseDto.class)
			.getBody();
	}

	@Recover
	public KakaoApiResponseDto recover(Exception exception, String address) {
		log.error("now : {} Kakao Address Search API 호출 중 에러가 발생했습니다. address: {}", now(), address, exception);
		return KakaoApiResponseDto.isEmpty();
	}
}
