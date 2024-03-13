package app.pharmacy.direction.service;

import app.pharmacy.api.kakao.dto.DocumentDto;
import app.pharmacy.pharmacy.dto.PharmacyDto;
import app.pharmacy.pharmacy.service.PharmacySearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@MockitoSettings
class DirectionServiceTest {

	@Mock
	private PharmacySearchService pharmacySearchService;

	@InjectMocks
	private DirectionService directionService;// = new DirectionService(pharmacySearchService);

	private List<PharmacyDto> pharmacyList;


	@BeforeEach
	void setUp() {

		pharmacyList = new ArrayList<>();
		PharmacyDto pharmacyDto_1 = PharmacyDto.builder()
			.id(1L)
			.pharmacyName("돌곶이온누리약국")
			.pharmacyAddress("주소1")
			.latitude(37.61040424)
			.longitude(127.0569046)
			.build();
		PharmacyDto pharmacyDto_2 = PharmacyDto.builder()
			.id(2L)
			.pharmacyName("호수온누리약국")
			.pharmacyAddress("주소2")
			.latitude(37.60894036)
			.longitude(127.029052)
			.build();
		pharmacyList.add(pharmacyDto_1);
		pharmacyList.add(pharmacyDto_2);
	}

	@Test
	void 거리를_계산할_수_있다() {
		//given
		double latitude1 = 37.5505;
		double longitude1 = 127.0817;
		double latitude2 = 37.541;
		double longitude2 = 127.0766;
		String result = "1.1";

		DirectionService.ValueObject valueObject = DirectionService.ValueObject.builder()
			.lat1(latitude1)
			.lon1(longitude1)
			.lat2(latitude2)
			.lon2(longitude2)
			.build();
		//when:
		double haversine = directionService.haversine(valueObject);
		System.out.println("haversine = " + haversine);

		//then
		assertEquals(String.format("%.1f", haversine), result);
	}

	@Test
	void 결과값_거리순으로_정렬된다() {
		String addressName = "서울 성북구 종암로10길";
		double inputLatitude = 37.5960650456809;
		double inputLongitude = 127.037033003036;
		DocumentDto documentDto = DocumentDto.builder()
			.addressName(addressName)
			.latitude(inputLatitude)
			.longitude(inputLongitude)
			.build();

		when(pharmacySearchService.searchPharmacyDtoList()).thenReturn(pharmacyList);
		var directions = directionService.buildDirection(documentDto);

		assertEquals(2, directions.size());
		assertEquals("호수온누리약국", directions.get(0).getTargetPharmacyName());
		assertEquals("돌곶이온누리약국", directions.get(1).getTargetPharmacyName());
	}

	@Test
	void 결과값_최대_거리_10km_이내로_반환된다() {
		pharmacyList.add(
			PharmacyDto.builder()
				.id(3L)
				.pharmacyName("경기약국")
				.pharmacyAddress("주소3")
				.latitude(37.3825107393401)
				.longitude(127.236707811313)
				.build()
		);

		String addressName = "서울 성북구 종암로10길";
		double inputLatitude = 37.5960650456809;
		double inputLongitude = 127.037033003036;

		DocumentDto documentDto = DocumentDto.builder()
			.addressName(addressName)
			.latitude(inputLatitude)
			.longitude(inputLongitude)
			.build();

		when(pharmacySearchService.searchPharmacyDtoList()).thenReturn(pharmacyList);
		var directions = directionService.buildDirection(documentDto);

		assertEquals(2, directions.size());
		assertEquals("호수온누리약국", directions.get(0).getTargetPharmacyName());
		assertEquals("돌곶이온누리약국", directions.get(1).getTargetPharmacyName());

	}

	@Test
	void 사용자_정보가_없으면_빈_리스트를_반환한다() {
		DocumentDto documentDto = DocumentDto.builder()
			.addressName("")
			.latitude(0)
			.longitude(0)
			.build();

		when(pharmacySearchService.searchPharmacyDtoList()).thenReturn(pharmacyList);
		var directions = directionService.buildDirection(documentDto);

		assertEquals(0, directions.size());
	}


}
