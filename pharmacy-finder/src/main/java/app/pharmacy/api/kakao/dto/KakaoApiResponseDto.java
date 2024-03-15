package app.pharmacy.api.kakao.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString(of = {"meta", "documents"})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"meta", "documents"})
public class KakaoApiResponseDto {

    @JsonProperty("meta")
    private MetaDto meta;

    @JsonProperty("documents")
    private List<DocumentDto> documents;


	public static KakaoApiResponseDto empty(){
		return new KakaoApiResponseDto(null, List.of());
	}

	public boolean isListEmpty(){
		return documents.isEmpty();
	}
}
