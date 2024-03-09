package app.pharmacy.api.kakao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI buildAddressSearchUri(String query) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS);
        builder.queryParam("query", query);

        URI uri = builder.build().encode(UTF_8).toUri();
        log.info("{} uri : {}", this.getClass(), uri);

        return uri;
    }

}