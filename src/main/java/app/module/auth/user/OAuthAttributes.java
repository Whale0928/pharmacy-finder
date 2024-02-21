package app.module.auth.user;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", attributes -> new OAuth2UserInfo(
            attributes,
            attributes.get("sub").toString(),
            attributes.get("name").toString(),
            attributes.get("email").toString()
    )),

    NAVER("naver", attributes -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return new OAuth2UserInfo(
                response,
                response.get("id").toString(),
                response.get("name").toString(),
                response.get("email").toString()
        );
    }),
    ;


    private final String registrationId; // OAuth2 로그인 진행시 키가 되는 필드값을 의미
    private final Function<Map<String, Object>, OAuth2UserInfo> of; // OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 하나하나 변환해야함

    OAuthAttributes(String registrationId, Function<Map<String, Object>, OAuth2UserInfo> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static OAuth2UserInfo of(String providerId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> provider.registrationId.equals(providerId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
