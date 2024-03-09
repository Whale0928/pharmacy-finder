package app.pharmacy.pharmacy.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PharmacyRepositoryTest {
    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Test
    void 약국을_저장할_수_있다() {

    }

}