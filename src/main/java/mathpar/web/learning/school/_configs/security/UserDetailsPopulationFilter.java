package mathpar.web.learning.school._configs.security;//package mathpar.web.frontend.application._configs.security;

import mathpar.web.learning.school.services.UserProfileService;
import mathpar.web.learning.school.utils.Constants;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserDetailsPopulationFilter implements Filter {
    private final UserProfileService userProfileService;

    public UserDetailsPopulationFilter(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var rawAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if(rawAuthentication == null){
            ((HttpServletResponse)response).sendError(403, "Authentication wasn't found");
            return;
        }

        var profileId = ((HttpServletRequest)request).getHeader(Constants.PROFILE_HEADER_NAME);
        if (rawAuthentication instanceof UserAuthentication && profileId!=null) {
            try {
                var authentication = (UserAuthentication) rawAuthentication;
                var profile = userProfileService.getProfile(Long.parseLong(profileId));
                assert profile.getSchool()!=null;
                authentication.setUserDetails(new UserAuthentication.UserDetails(profile));
            }catch (NumberFormatException e){
                ((HttpServletResponse) response).sendError(400, "Profile id should be valid number");
            }
        }
        chain.doFilter(request, response);
    }
}
