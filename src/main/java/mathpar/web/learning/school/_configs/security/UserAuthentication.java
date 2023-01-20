package mathpar.web.learning.school._configs.security;//package mathpar.web.frontend.application._configs.security;

import lombok.Getter;
import mathpar.web.learning.school.entities.School;
import mathpar.web.learning.school.entities.UserProfile;
import mathpar.web.learning.school.utils.SecurityUtils;
import mathpar.web.learning.school.utils.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class UserAuthentication implements Authentication {
    private final String token;
    private UserDetails userDetails;
    private List<? extends GrantedAuthority> authorities;

    public UserAuthentication(String token) {
        this.token = token;
    }

    void propagateAuthorities(List<Role> roles){
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
        authorities = List.of(new SimpleGrantedAuthority("ROLE_"+userDetails.profile.getRole().name().toUpperCase()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public UserDetails getDetails() {
        return userDetails;
    }

    @Override
    public Long getPrincipal() {
        return SecurityUtils.extractAccountId(token);
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return token;
    }

    @Getter
    public static class UserDetails {
        private final UserProfile profile;
        private final School school;

        UserDetails(UserProfile profile) {
            this.profile = profile;
            this.school = profile.getSchool();
        }
    }

}
