package app.module.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final CustomUserDetailService customUserDetailService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()

                .formLogin().disable() // 시큐리티 기본 로그인 페이지 사용 안함
                .httpBasic().disable()  // ajax등 다양한 방식으로 통신하기 위해 기본 설정을 해제

                .headers().frameOptions().disable()

                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS) // 서버 상태 관리 : 무상태

                .and()
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/oauth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .userInfoEndpoint()
                        .userService(customUserDetailService)
                        .and()
                        .successHandler(oAuth2LoginSuccessHandler))
                .build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 응답시 json을 자바스크립트에서 처리 가능 여부
        config.addAllowedOrigin("*");    // 모든 ip에 응답을 허용
        config.addAllowedHeader("*");  // 모든 header에 응답을 허용
        config.addAllowedMethod("*");  // 모든 post, get, put, delete, patch 요청을 허용
        source.registerCorsConfiguration("/**", config); // 모든 url에 대해 위의 설정을 적용
        return new CorsFilter(source);
    }
}
