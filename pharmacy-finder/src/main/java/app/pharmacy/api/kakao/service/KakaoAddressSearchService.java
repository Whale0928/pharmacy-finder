package app.pharmacy.api.kakao.service;

import app.pharmacy.api.kakao.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

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

    public KakaoApiResponseDto requestAddressSearch(String address) {
        Objects.requireNonNull(address, "address must not be null");

        URI uri = kakaoUriBuilderService.buildAddressSearchUri(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate
                .exchange(uri, GET, entity, KakaoApiResponseDto.class)
                .getBody();
    }
}
