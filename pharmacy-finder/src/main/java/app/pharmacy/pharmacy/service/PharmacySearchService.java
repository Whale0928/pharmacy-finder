package app.pharmacy.pharmacy.service;

import app.pharmacy.pharmacy.dto.PharmacyDto;
import app.pharmacy.pharmacy.entity.Pharmacy;
import app.pharmacy.pharmacy.repository.PharmacyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Getter
@RequiredArgsConstructor
@Service
public class PharmacySearchService {
	private final PharmacyRepository repository;

	public List<PharmacyDto> searchPharmacyDtoList() {
		return repository.findAll().stream().map(this::convertToPharmacyDto).toList();
	}

	private PharmacyDto convertToPharmacyDto(Pharmacy pharmacy) {
		return PharmacyDto.builder()
			.id(pharmacy.getId())
			.pharmacyName(pharmacy.getPharmacyName())
			.pharmacyAddress(pharmacy.getPharmacyAddress())
			.latitude(pharmacy.getLatitude())
			.longitude(pharmacy.getLongitude())
			.build();
	}

}
