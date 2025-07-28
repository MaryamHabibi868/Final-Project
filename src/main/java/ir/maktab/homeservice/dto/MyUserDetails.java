package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;


public class MyUserDetails implements UserDetails {

    @Getter
    private final User user;

   /* private final Collection<GrantedAuthority> authorities = new ArrayList<>();*/

    public MyUserDetails(User user) {
        this.user = user;
    }

   /* private void fillAuthorities() {
        if (user.getRole() != null) {
            authorities.add(
                    new SimpleGrantedAuthority(user.getRole().name())
            );

            *//*if (user.getRole().getAuthorities() != null) {
                user.getRole().getAuthorities().forEach(authority ->
                        authorities.add(new SimpleGrantedAuthority(authority.getName()))
                );*//*
            }
        }*/


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }
}
