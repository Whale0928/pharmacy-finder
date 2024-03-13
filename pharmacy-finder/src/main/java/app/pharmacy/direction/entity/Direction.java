package app.pharmacy.direction.entity;

import app.pharmacy.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "direction")
public class Direction extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("사용자 주소")
	private String inputAddress;
	@Comment("사용자 위도")
	private double inputLatitude;
	@Comment("사용자 경도")
	private double inputLongitude;

	@Comment("대상 약국 이름")
	private String targetPharmacyName;
	@Comment("대상 약국 주소")
	private String targetAddress;
	@Comment("대상 약국 위도")
	private double targetLatitude;
	@Comment("대상 약국 경도")
	private double targetLongitude;

	@Comment("차이 거리")
	private double distance;

	@Builder
	public Direction(Long id, String inputAddress, double inputLatitude, double inputLongitude, String targetPharmacyName, String targetAddress, double targetLatitude, double targetLongitude, double distance) {
		this.id = id;
		this.inputAddress = inputAddress;
		this.inputLatitude = inputLatitude;
		this.inputLongitude = inputLongitude;
		this.targetPharmacyName = targetPharmacyName;
		this.targetAddress = targetAddress;
		this.targetLatitude = targetLatitude;
		this.targetLongitude = targetLongitude;
		this.distance = distance;
	}
}
