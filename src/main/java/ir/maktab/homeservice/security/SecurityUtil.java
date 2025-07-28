package ir.maktab.homeservice.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public String getCurrentUsername() {
        return ((UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal())
                .getUsername();
    }

    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}

