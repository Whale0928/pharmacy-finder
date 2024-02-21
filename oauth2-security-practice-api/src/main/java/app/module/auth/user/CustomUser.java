package app.module.auth.user;

import app.module.member.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


@Getter
public class CustomUser implements UserDetails, OAuth2User {

    private final Member user;
    private final OAuth2UserInfo oauth2Oauth2UserInfo;

    public CustomUser(Member user, OAuth2UserInfo oauth2Oauth2UserInfo) {
        this.user = user;
        this.oauth2Oauth2UserInfo = oauth2Oauth2UserInfo;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2Oauth2UserInfo.getAttributes();
    }


    @Override
    public String getName() {
        return oauth2Oauth2UserInfo.getName();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
