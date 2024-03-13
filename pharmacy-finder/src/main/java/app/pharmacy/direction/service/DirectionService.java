package app.pharmacy.direction.service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@RequiredArgsConstructor
@Service
public class DirectionService {
	private final double EARTH_RADIUS = 6371; //지구 반지름

	//Haversine formula ( 위경도 기반의 거리 계산 공식)
	public double haversine(ValueObject value) {
		log.info("haversine calculation start , {}", EARTH_RADIUS);
		return Math.acos(
			Math.sin(value.lat1())
				* Math.sin(value.lat2())
				+ Math.cos(value.lat1())
				* Math.cos(value.lat2())
				* Math.cos(value.lon2() - value.lon1())
		);
	}

	public record ValueObject(double lat1, double lon1, double lat2, double lon2) {
		@Builder
		public ValueObject(double lat1, double lon1, double lat2, double lon2) {
			this.lat1 = Math.toRadians(lat1);
			this.lon1 = Math.toRadians(lon1);
			this.lat2 = Math.toRadians(lat2);
			this.lon2 = Math.toRadians(lon2);
		}
	}
}

