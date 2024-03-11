package app.pharmacy.pharmacy.service;

import app.pharmacy.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyService {
    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public String changeAddress(Long id, String newAddress) {
        var pharmacy = pharmacyRepository.findById(id)
                .orElse(null);

        if (Objects.isNull(pharmacy)) {
            return "Pharmacy with id " + id + " not found";
        }

        return pharmacy.changePharmacyAddress(newAddress);
    }
}
