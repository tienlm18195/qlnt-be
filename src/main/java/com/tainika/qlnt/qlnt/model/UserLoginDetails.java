package com.tainika.qlnt.qlnt.model;

import com.tainika.qlnt.qlnt.constants.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserLoginDetails implements UserDetails {
    private String userName;
    private String password;
    private Integer status;
    private List<SimpleGrantedAuthority> authorities;

    public UserLoginDetails() {}

    public UserLoginDetails(User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.authorities = user.getRole().getAuthorities()
            .stream()
            .map(a -> new SimpleGrantedAuthority(a.getCode()))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !Status.USER.DELETED.getCode().equals(status);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Status.USER.LOCK.getCode().equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return (Status.USER.ACTIVE.getCode().equals(status) || Status.USER.TEMPORARY.getCode().equals(status));
    }
}
