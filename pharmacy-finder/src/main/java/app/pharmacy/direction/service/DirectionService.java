package app.pharmacy.direction.service;

import app.pharmacy.api.kakao.dto.DocumentDto;
import app.pharmacy.api.kakao.service.KakaoCategorySearchService;
import app.pharmacy.direction.entity.Direction;
import app.pharmacy.direction.repository.DirectionRepository;
import app.pharmacy.pharmacy.dto.PharmacyDto;
import app.pharmacy.pharmacy.service.PharmacySearchService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Getter
@RequiredArgsConstructor
@Service
public class DirectionService {

	private static final double EARTH_RADIUS = 6371; //지구 반지름
	private static final int MAX_RESULT_COUNT = 3; //최대 반환값 개수
	private static final double MAX_DISTANCE = 10.0; //최대 거리

	private final PharmacySearchService searchService;
	private final KakaoCategorySearchService categorySearchService;

	private final DirectionRepository directionRepository;


	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	public List<Direction> saveAll(List<Direction> list) {
		if (CollectionUtils.isEmpty(list))
			return Collections.emptyList();

		return directionRepository.saveAll(list);
	}

	/**
	 * 카테고리별 Direction 목록을 반환한다.
	 * Build category list.
	 *
	 * @param userInfo the user info
	 * @return the list
	 */
	public List<Direction> buildDirectionByCategory(DocumentDto userInfo) {
		if (Objects.isNull(userInfo)) {
			return Collections.emptyList();
		}


		return categorySearchService.requestCategorySearch(userInfo.getLatitude(), userInfo.getLongitude(), MAX_DISTANCE)
			.getDocuments()
			.stream()
			.map(documentDto -> Direction.builder()
				.inputAddress(userInfo.getAddressName())
				.inputLongitude(userInfo.getLongitude())
				.inputLatitude(userInfo.getLatitude())
				.targetPharmacyName(documentDto.getPlaceName())
				.targetAddress(documentDto.getAddressName())
				.targetLongitude(documentDto.getLongitude())
				.targetLatitude(documentDto.getLatitude())
				.distance(documentDto.getDistance() * 0.001) //m -> km
				.build())
			.limit(MAX_RESULT_COUNT)
			.toList();
	}

	/**
	 * Direction 목록을 반환한다.
	 * Build direction list.
	 *
	 * @param userInfo the user info
	 * @return the list
	 */
	public List<Direction> buildDirection(DocumentDto userInfo) {
		if (Objects.isNull(userInfo)) {
			return Collections.emptyList();
		}

		return searchService.searchPharmacyDtoList()
			.stream()
			.map(v -> convertToDirection(userInfo, v))
			.filter(v -> v.getDistance() <= MAX_DISTANCE)
			.sorted(Comparator.comparing(Direction::getDistance))
			.limit(MAX_RESULT_COUNT)
			.collect(Collectors.toList());
	}

	/**
	 * Document, Pharmacy 정보를 받아 Direction으로 변환한다.
	 * Convert to direction direction.
	 *
	 * @param document the document
	 * @param pharmacy the pharmacy
	 * @return the direction
	 */
	private Direction convertToDirection(DocumentDto document, PharmacyDto pharmacy) {
		return Direction.builder()
			.inputAddress(document.getAddressName())
			.inputLongitude(document.getLongitude())
			.inputLatitude(document.getLatitude())
			.targetPharmacyName(pharmacy.pharmacyName())
			.targetAddress(pharmacy.pharmacyAddress())
			.targetLongitude(pharmacy.longitude())
			.targetLatitude(pharmacy.latitude())
			.distance(
				haversine(
					ValueObject.builder()
						.lat1(document.getLatitude())
						.lon1(document.getLongitude())
						.lat2(pharmacy.latitude())
						.lon2(pharmacy.longitude())
						.build())
			)
			.build();
	}

	/**
	 * Haversine을 계산한다.
	 * Haversine double.
	 *
	 * @param value the value
	 * @return the double
	 */
	public double haversine(ValueObject value) {
		return EARTH_RADIUS * Math.acos(Math.sin(value.lat1())
			* Math.sin(value.lat2())
			+ Math.cos(value.lat1())
			* Math.cos(value.lat2())
			* Math.cos(value.lon1() - value.lon2()));
	}

	/**
	 * 경도 및 위도를 라디안으로 변환한 Value Object
	 */
	public record ValueObject(double lat1, double lon1, double lat2, double lon2) {
		@Builder
		public ValueObject(double lat1, double lon1, double lat2, double lon2) {
			this.lat1 = Math.toRadians(lat1);
			this.lon1 = Math.toRadians(lon1);
			this.lat2 = Math.toRadians(lat2);
			this.lon2 = Math.toRadians(lon2);
		}
	}


}

