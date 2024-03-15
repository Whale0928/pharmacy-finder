package app.pharmacy.pharmacy.service;

import app.pharmacy.api.kakao.dto.DocumentDto;
import app.pharmacy.api.kakao.service.KakaoAddressSearchService;
import app.pharmacy.direction.entity.Direction;
import app.pharmacy.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 약국 추천 서비스 클래스
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class PharmacyRecommendationService {
	private final KakaoAddressSearchService kakaoMapAPiService;
	private final DirectionService directionService;

	public Object recommend(String address) {

		//카카오 api로 위치 정보 요청
		var kakaoApiResponse = kakaoMapAPiService.requestAddressSearch(address);

		if (Objects.isNull(kakaoApiResponse) || kakaoApiResponse.isListEmpty()) {
			log.error("{} :: Kakao API response is null", LocalDateTime.now());
			return Optional.empty();
		}
		log.info("{} :: Kakao API response :: {}", LocalDateTime.now(), kakaoApiResponse);

		//가장 가까운 약국 목록 조회
		DocumentDto documentDto = kakaoApiResponse.getDocuments().get(0);
		log.info("{} documentDto :: {}", LocalDateTime.now(), documentDto);

		//List<Direction> directions = directionService.buildDirection(documentDto);
		List<Direction> directions = directionService.buildDirectionByCategory(documentDto);
		log.info("{} directions :: {}", LocalDateTime.now(), directions);

		List<Direction> list = directionService.saveAll(directions);

		return list;
	}
}
