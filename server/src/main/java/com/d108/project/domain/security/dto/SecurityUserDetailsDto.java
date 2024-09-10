package com.d108.project.domain.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@Getter
@AllArgsConstructor
public class SecurityUserDetailsDto implements UserDetails {

    @Delegate
    private SecurityUserDto securityUserDto;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(securityUserDto.getRoleType().toString()));
    }

    public static SecurityUserDetailsDto from(SecurityUserDto securityUserDto, Collection<? extends GrantedAuthority> authorities) {
        return SecurityUserDetailsDto.builder()
                .securityUserDto(securityUserDto)
                .authorities(authorities)
                .build();
    }

    @Override
    public String getPassword() {
        return securityUserDto.getPassword();
    }

    @Override
    public String getUsername() {
        return securityUserDto.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
