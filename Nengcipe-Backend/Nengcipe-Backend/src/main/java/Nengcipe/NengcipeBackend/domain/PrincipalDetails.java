package Nengcipe.NengcipeBackend.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class PrincipalDetails implements UserDetails {
    private Member member;

    public PrincipalDetails(Member member) {
        this.member=member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //유저에게 부여된 권한들 반환
        return null;

    }

    @Override
    public String getPassword() {
        return member.getMemberPwd();
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() { //계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } //계정 잠김 여부

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } //credentials(password) 만료 여부

    @Override
    public boolean isEnabled() {
        return true;
    } //유저 사용 가능 여부
}