package app.multimodule.oauth2securitypractice.jwt;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
    @Bean
    public TokenProvider jwtTokenProvider(JwtProperties jwtProperties) {
        return new TokenProvider(jwtProperties.getSecret(), jwtProperties.getAccessTokenValidityInSeconds());
    }
}
