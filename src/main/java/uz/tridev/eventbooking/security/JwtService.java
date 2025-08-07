package uz.tridev.eventbooking.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.tridev.eventbooking.entity.Role;
import uz.tridev.eventbooking.entity.User;
import uz.tridev.eventbooking.entity.enums.RoleName;
import uz.tridev.eventbooking.repository.RoleRepository;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtService {

    private final RoleRepository roleRepository;

    @Value("${jwt.secret}")
    private String secret;

    public JwtService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());

        String rolesString = user.getRoles() != null && !user.getRoles().isEmpty()
                ? user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.joining(","))
                : "";

        claims.put("roles", rolesString);

        return Jwts.builder()
                .subject(user.getUsername())
                .claims(claims)
                .signWith(signKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .compact();
    }

    private SecretKey signKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(signKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Failed to extract username from token: {}", e.getMessage());
            return null;
        }
    }

    public List<Role> getRoles(String token) {
        try {
            Claims claims = getClaims(token);
            String strRoles = (String) claims.get("roles");

            if (!StringUtils.hasText(strRoles)) {
                log.debug("No roles found in token");
                return new ArrayList<>();
            }

            List<RoleName> roleNames = Arrays.stream(strRoles.split(","))
                    .filter(StringUtils::hasText)
                    .map(String::trim)
                    .map(roleString -> {
                        try {
                            return RoleName.valueOf(roleString);
                        } catch (IllegalArgumentException e) {
                            log.warn("Invalid role name found in token: {}", roleString);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (roleNames.isEmpty()) {
                log.debug("No valid roles found in token");
                return new ArrayList<>();
            }

            return roleRepository.findAllByRoleNameIn(roleNames);

        } catch (Exception e) {
            log.error("Failed to extract roles from token: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public UUID getUserId(String token) {
        try {
            Claims claims = getClaims(token);
            String idString = (String) claims.get("id");
            return StringUtils.hasText(idString) ? UUID.fromString(idString) : null;
        } catch (Exception e) {
            log.error("Failed to extract user ID from token: {}", e.getMessage());
            return null;
        }
    }
}
