package app.module.auth.service;

import app.module.auth.user.CustomUser;
import app.module.auth.user.OAuth2UserInfo;
import app.module.auth.user.OAuthAttributes;
import app.module.member.Member;
import app.module.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Component
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private static final String PASSWORD = "password";
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // userRequest를 통해 사용자 정보 가져오기
        OAuth2UserInfo oAuth2UserInfo = OAuthAttributes.of(registrationId, super.loadUser(userRequest).getAttributes());
        Member member = saveUser(oAuth2UserInfo);
        return new CustomUser(member, oAuth2UserInfo);
    }

    private Member saveUser(OAuth2UserInfo oauth2Oauth2UserInfo) {
        String email = oauth2Oauth2UserInfo.getEmail();
        String name = oauth2Oauth2UserInfo.getName();
        String password = passwordEncoder.encode(PASSWORD + randomUUID().toString().substring(0, 8));

        Member member = Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        // 유저정보가 없으면 저장 있으면 혹시라도 변경된 정보 업데이트
        memberRepository.findUserByEmail(email)
                .ifPresentOrElse(
                        entity -> entity.update(member.getEmail(), member.getName()),
                        () -> memberRepository.save(member)
                );

        return member;
    }

}
