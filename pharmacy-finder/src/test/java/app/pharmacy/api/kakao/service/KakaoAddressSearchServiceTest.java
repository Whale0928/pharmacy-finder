package app.pharmacy.api.kakao.service;

import app.pharmacy.AbstractIntegrationContainerBaseTest;
import app.pharmacy.api.kakao.dto.KakaoApiResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService service;


    @Test
    void Address_파라미터가_null일_경우_null을_반환한다() {
        // given
        String address = null;

        KakaoApiResponseDto kakaoApiResponseDto = service.requestAddressSearch(address);

        assertNull(kakaoApiResponseDto);
    }

    @Test
    void 주소값이_정상적일_경우_제대로_조회된다() {
        String address = "자양동13-5";
        KakaoApiResponseDto response = service.requestAddressSearch(address);

        assertNotNull(response);
        assertNotNull(response.getMeta());
        assertNotNull(response.getDocuments());

    }

}