package app.pharmacy.pharmacy.service;

import app.pharmacy.AbstractIntegrationContainerBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PharmacyRecommendationServiceTest extends AbstractIntegrationContainerBaseTest {

	@Autowired
	private PharmacyRecommendationService pharmacyRecommendationService;

	@Test
	@DisplayName("약국 추천 서비스 테스트")
	void call_1(){
		String address = "서울특별시 강남구 역삼동 736-1";
		pharmacyRecommendationService.recommend(address);
	}

}
