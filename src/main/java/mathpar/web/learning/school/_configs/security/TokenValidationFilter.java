package mathpar.web.learning.school._configs.security;//package mathpar.web.frontend.application._configs.security;

import mathpar.web.learning.school.services.AccountService;
import mathpar.web.learning.school.services.UserProfileService;
import mathpar.web.learning.school.utils.Constants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TokenValidationFilter implements Filter {
    private final AccountService accountService;
    private final UserProfileService userProfileService;

    protected TokenValidationFilter(AccountService accountService, UserProfileService userProfileService) {
        this.accountService = accountService;
        this.userProfileService = userProfileService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var token = ((HttpServletRequest) request).getHeader(Constants.TOKEN_HEADER_NAME);
        var context = SecurityContextHolder.getContext();
        if (token == null){
            context.setAuthentication(new AnonymousAuthenticationToken("anonymous", "anonymous", List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
        }
        else {
            if(!accountService.isTokenValid(token)){
                sendError(response, 403, "Token is invalid");
                return;
            }
            context.setAuthentication(new UserAuthentication(token));
        }
        chain.doFilter(request, response);
    }

    private void sendError(ServletResponse response, int status, String message) throws IOException {
        ((HttpServletResponse)response).sendError(status, message);
    }
}
