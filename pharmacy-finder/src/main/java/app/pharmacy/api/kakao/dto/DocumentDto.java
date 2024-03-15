package app.pharmacy.api.kakao.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(of = {"placeName", "addressName", "latitude", "longitude", "distance"})
public class DocumentDto {

	@JsonProperty("place_name")
	private String placeName; //장소명

	@JsonProperty("address_name")
	private String addressName;

	@JsonProperty("y")
	private double latitude; // 위도

	@JsonProperty("x")
	private double longitude; // 경도

	@JsonProperty("distance")
	private Double distance; // 중심좌표까지의 거리

	@Builder
	public DocumentDto(String placeName, String addressName, double latitude, double longitude, Double distance) {
		this.placeName = placeName;
		this.addressName = addressName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
	}
}
