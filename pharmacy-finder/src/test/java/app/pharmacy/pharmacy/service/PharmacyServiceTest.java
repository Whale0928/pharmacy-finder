package app.pharmacy.pharmacy.service;

import app.pharmacy.AbstractIntegrationContainerBaseTest;
import app.pharmacy.pharmacy.entity.Pharmacy;
import app.pharmacy.pharmacy.repository.PharmacyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PharmacyServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @BeforeEach
    void setUp() {
        pharmacyRepository.deleteAll();
        pharmacyRepository.flush();
    }

    @Test
    void 주소를_변경할_수_있다() {
        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyAddress("서울시 강남구")
                .pharmacyName("강남약국")
                .latitude(37.1234)
                .longitude(127.1234)
                .build();

        Pharmacy saved = pharmacyRepository.save(pharmacy);
        pharmacyService.changeAddress(saved.getId(), "서울시 광진구 자양동");

        Pharmacy result = pharmacyRepository.findById(saved.getId()).get();

        assertEquals("서울시 광진구 자양동", result.getPharmacyAddress());
    }
}