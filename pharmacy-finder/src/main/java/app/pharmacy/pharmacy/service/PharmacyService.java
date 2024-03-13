package app.pharmacy.pharmacy.service;

import app.pharmacy.pharmacy.entity.Pharmacy;
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
	public void changeAddress(Long id, String newAddress) {
		var pharmacy = pharmacyRepository.findById(id)
			.orElse(null);

		if (Objects.isNull(pharmacy)) {
			return;
		}

		pharmacy.changePharmacyAddress(newAddress);
	}

	@Transactional
	public Pharmacy save(Pharmacy pharmacy) {
		return pharmacyRepository.save(pharmacy);
	}
}
