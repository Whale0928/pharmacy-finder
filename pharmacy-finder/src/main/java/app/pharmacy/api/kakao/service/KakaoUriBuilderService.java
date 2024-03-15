package app.pharmacy.api.kakao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.LocalTime.now;

@Slf4j
@Service
public class KakaoUriBuilderService {

	private static final String KAKAO_LOCAL_SEARCH_ADDRESS = "https://dapi.kakao.com/v2/local/search/address.json";

	private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

	public URI buildAddressSearchUri(String query) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS);
		builder.queryParam("query", query);

		URI uri = builder.build().encode(UTF_8).toUri();
		log.info("{} uri : {}", this.getClass(), uri);

		return uri;
	}

	/**
	 * Build category search uri uri.
	 *
	 * @param category 검색 카테고리
	 * @param lat      위도
	 * @param lon      경도
	 * @param radius   반경
	 * @return the uri
	 */
	public URI buildCategorySearchUri(String category, double lat, double lon, double radius) {

		Double meterRadius = radius * 1000; //미터단위 변환

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_CATEGORY_SEARCH_URL);
		builder.queryParam("category_group_code", category);
		builder.queryParam("x", lon);
		builder.queryParam("y", lat);
		builder.queryParam("radius", meterRadius);
		builder.queryParam("sort", "distance");

		URI uri = builder.build().encode(UTF_8).toUri();
		log.info("{} uri : {}", now(), uri);

		return uri;
	}

}
