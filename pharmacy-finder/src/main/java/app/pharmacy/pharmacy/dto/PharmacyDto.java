package app.pharmacy.pharmacy.dto;

import lombok.Builder;

public record PharmacyDto(Long id, String pharmacyName, String pharmacyAddress, double latitude, double longitude) {
	@Builder
	public PharmacyDto {
	}
}
