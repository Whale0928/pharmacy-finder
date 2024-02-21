package app.module.auth.handler;

import app.module.auth.user.CustomUser;
import app.module.jwt.Service.JwtService;
import app.module.jwt.Service.TokenMapping;
import app.module.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String TOKEN = "token";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String REDIRECT_URL = "http://localhost:3000/login/redirect";

    private final JwtService jwtService;
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TokenMapping tokenMapping = saveUser(authentication);
        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(tokenMapping));
    }

    private TokenMapping saveUser(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        String email = customUser.getUser().getEmail();
        TokenMapping token = jwtService.createToken(email);

        memberRepository.findUserByEmail(email).get()
                .updateRefreshToken(token.getRefreshToken());

        return token;
    }

    private String getRedirectUrl(TokenMapping token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_URL)
                .queryParam(TOKEN, token.getAccessToken())
                .queryParam(REFRESH_TOKEN, token.getRefreshToken())
                .build().toUriString();
    }
}
