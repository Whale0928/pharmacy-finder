package app.pharmacy.api.kakao.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KakaoUriBuilderServiceTest {

    private KakaoUriBuilderService kakaoUriBuilderService;

    @BeforeEach
    void setUp() {
        kakaoUriBuilderService = new KakaoUriBuilderService();
    }


    @Test
    void 약국을_검색할_수_있다() {
        // given
        String query = "서울특별시 강남구 역삼동 736-1";
        Charset charset = StandardCharsets.UTF_8;

        // when
        URI uri = kakaoUriBuilderService.buildAddressSearchUri(query);
        String decode = URLDecoder.decode(uri.toString(), charset);

        // then
        assertEquals("https://dapi.kakao.com/v2/local/search/address.json?query=" + query, decode);
    }
}