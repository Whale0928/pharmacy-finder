package app.pharmacy.api.kakao.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"addressName", "latitude", "longitude"})
@ToString(of = {"addressName", "latitude", "longitude"})
public class DocumentDto {

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("y")
    private double latitude; // 위도

    @JsonProperty("x")
    private double longitude; // 경도

	@Builder
	public DocumentDto(String addressName, double latitude, double longitude) {
		this.addressName = addressName;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
