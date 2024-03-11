package app.pharmacy.api.kakao.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"addressName", "latitude", "longitude"})
public class DocumentDto {

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("y")
    private double latitude; // 위도

    @JsonProperty("x")
    private double longitude; // 경도
}
