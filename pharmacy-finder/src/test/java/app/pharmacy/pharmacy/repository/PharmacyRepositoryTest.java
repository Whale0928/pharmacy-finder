package app.pharmacy.pharmacy.repository;

import app.pharmacy.AbstractIntegrationContainerBaseTest;
import app.pharmacy.pharmacy.entity.Pharmacy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {
	@Autowired
	private PharmacyRepository pharmacyRepository;

	@BeforeEach
	void setUp() {
		pharmacyRepository.deleteAll();
		pharmacyRepository.flush();
	}

	@Test
	void 약국을_저장할_수_있다() {

		Pharmacy pharmacy = Pharmacy.builder()
			.pharmacyName("약국1")
			.pharmacyAddress("서울시 강남구")
			.latitude(37.1234)
			.longitude(127.1234)
			.build();

		Pharmacy saved = pharmacyRepository.save(pharmacy);

		assertEquals(pharmacy.getId(), saved.getId());
		assertEquals(pharmacy.getPharmacyName(), saved.getPharmacyName());
	}
	@Test
	void 약국들을_저장할_수_있다() {

		Pharmacy pharmacy_1 = Pharmacy.builder()
			.pharmacyName("약국1")
			.pharmacyAddress("서울시 강남구")
			.latitude(37.1234)
			.longitude(127.1234)
			.build();

		Pharmacy pharmacy_2 = Pharmacy.builder()
			.pharmacyName("약국2")
			.pharmacyAddress("서울시 강북구")
			.latitude(37.1234)
			.longitude(127.1234)
			.build();

		Pharmacy pharmacy_3 = Pharmacy.builder()
			.pharmacyName("약국3")
			.pharmacyAddress("서울시 강동구")
			.latitude(37.1234)
			.longitude(127.1234)
			.build();

		List<Pharmacy> pharmacyList = List.of(pharmacy_1, pharmacy_2, pharmacy_3);
		pharmacyRepository.saveAll(pharmacyList);

		List<Pharmacy> pharmacies = pharmacyRepository.findAll();

		assertEquals(3, pharmacyList.size());
		assertEquals(pharmacies.get(0).getPharmacyName(), pharmacy_1.getPharmacyName());
		assertEquals(pharmacies.get(1).getPharmacyName(), pharmacy_2.getPharmacyName());
	}
	@Test
	void 변경시간을_확인할_수_있다() throws InterruptedException {

		var prevAddress = "서울시 강남구";
		var nextAddress = "서울시 강북구";

		LocalDateTime now = now();

		var pharmacy = Pharmacy.builder()
			.pharmacyName("약국1")
			.pharmacyAddress(prevAddress)
			.latitude(37.1234)
			.longitude(127.1234)
			.build();

		Thread.sleep(10);
		Pharmacy saved = pharmacyRepository.save(pharmacy);

		System.out.println("now = " + now);
		System.out.println("saved.getCreatedDate() = " + saved.getCreatedDate());

		// when
		assertTrue(saved.getCreatedDate().isAfter(now));

	}
}
