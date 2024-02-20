package app.multimodule.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, unique = true, nullable = false)
    private String email;

    @Column(length = 65, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 11)
    private String mobile;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private MemberType type = MemberType.USER;

    @Builder
    protected Member(Long id, String email, String password, String name, String mobile, MemberType type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
        this.type = type;
    }
}