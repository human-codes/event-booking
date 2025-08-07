package uz.tridev.eventbooking.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.tridev.eventbooking.entity.Role;
import uz.tridev.eventbooking.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    private static final List<String> EXCLUDED_PATH_PREFIXES = List.of(
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources",
            "/webjars",
            "/api/auth"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (EXCLUDED_PATH_PREFIXES.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 🔐 JWT token tekshirish
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtService.isValid(token)) {
                String username = jwtService.getUsername(token);
                List<Role> roles = jwtService.getRoles(token);

                UserDetails userDetails = org.springframework.security.core.userdetails.User
                        .withUsername(username)
                        .authorities(roles)
                        .password("") // parol tekshirilmaydi
                        .build();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        roles
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println("Received Token: " + token);
                System.out.println("Token Validity: true");
                System.out.println("Username: " + username);
                System.out.println("Roles in Token: " + roles);
            }
        }

        filterChain.doFilter(request, response);
    }
}
