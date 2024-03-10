package app.pharmacy.pharmacy.repository;

import app.pharmacy.AbstractIntegrationContainerBaseTest;
import app.pharmacy.pharmacy.entity.Pharmacy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private PharmacyRepository pharmacyRepository;

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

}