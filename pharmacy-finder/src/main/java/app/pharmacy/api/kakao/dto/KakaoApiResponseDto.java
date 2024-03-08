package app.pharmacy.api.kakao.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoApiResponseDto {
    private MetaDto meta;
    private List<DocumentDto> documents;
}
