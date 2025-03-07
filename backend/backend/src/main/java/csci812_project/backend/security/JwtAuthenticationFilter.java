package csci812_project.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestPath = request.getServletPath();
        System.out.println("Incoming request to: " + requestPath); // ✅ Log every request

        // ✅ Skip JWT authentication for public endpoints
        if (requestPath.startsWith("/api/auth/register") ||
                requestPath.startsWith("/api/auth/login") ||
                requestPath.startsWith("/api/auth/verify")) {

            System.out.println("Skipping JWT authentication for: " + requestPath); // ✅ Log skipped authentication
            chain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            System.out.println("Valid token found for user: " + username); // ✅ Log token validation

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
            System.out.println("No valid token found for request to: " + requestPath); // 🚨 Log missing/invalid token
        }

        chain.doFilter(request, response);
    }


//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        String requestPath = request.getServletPath();
//
//        // ✅ Skip JWT authentication for public endpoints
//        if (requestPath.startsWith("/api/auth/register") ||
//                requestPath.startsWith("/api/auth/login") ||
//                requestPath.startsWith("/api/auth/verify")) {
//
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String token = getTokenFromRequest(request);
//        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
//            String username = jwtTokenProvider.getUsername(token);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            UsernamePasswordAuthenticationToken authToken =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//        }
//
//        chain.doFilter(request, response);
//    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
                ? bearerToken.substring(7)
                : null;
    }


}

