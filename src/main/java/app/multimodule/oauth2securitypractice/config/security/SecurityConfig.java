package app.multimodule.oauth2securitypractice.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.addFilter(corsFilter())

                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS) // 서버 상태 관리 : 무상태
                .and()

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/oauth/**").permitAll()
                        .anyRequest().authenticated()
                )

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
