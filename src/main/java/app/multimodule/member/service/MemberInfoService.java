package app.multimodule.member.service;

import app.multimodule.member.MemberInfo;
import app.multimodule.member.domain.Member;
import app.multimodule.member.domain.MemberRepository;
import app.multimodule.member.domain.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {
    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        MemberType type = Objects.requireNonNullElse(member.getType(), MemberType.USER);
        List<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(type.name()));

        return MemberInfo.builder()
                .email(member.getEmail())
                .name(member.getName())
                .member(member)
                .authorities(authorities)
                .build();
    }
}