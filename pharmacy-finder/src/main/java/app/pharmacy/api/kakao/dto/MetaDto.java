package app.pharmacy.api.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"totalCount"})
public class MetaDto {

    @JsonProperty("total_count")
    private Integer totalCount;

}
