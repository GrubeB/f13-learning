package pl.app.common.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import pl.app.common.security.user_details.CustomUserDetails;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

class TokenServiceImpl implements TokenService {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID_KEY = "user_id";
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    private final TokenGrantedAuthoritySerializer tokenGrantedAuthoritySerializer;

    public TokenServiceImpl(TokenGrantedAuthoritySerializer tokenGrantedAuthoritySerializer) {
        this.tokenGrantedAuthoritySerializer = tokenGrantedAuthoritySerializer;
    }

    @Override
    public String generateToken(Authentication authentication) {
        Map<String, Object> extraClaims = new HashMap<>();
        if (authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            extraClaims.put(USER_ID_KEY, customUserDetails.getUserId());
        }
        extraClaims.put(AUTHORITIES_KEY, tokenGrantedAuthoritySerializer.serialize(authentication.getAuthorities()));
        return generateToken(authentication, extraClaims);
    }

    @Override
    public String generateToken(Authentication authentication, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    @Override
    public Authentication extractAuthentication(String token) {
        try {
            final String userEmail = extractClaim(token, Claims::getSubject);
            final String userId = extractClaim(token, claims -> claims.get(USER_ID_KEY, String.class));
            Object authoritiesObject = extractClaim(token, claims -> claims.get(AUTHORITIES_KEY, Object.class));
            final List<? extends GrantedAuthority> authorities = tokenGrantedAuthoritySerializer.deserialize(authoritiesObject);
            final CustomUserDetails userDetails = new CustomUserDetails(UUID.fromString(userId), userEmail, authorities);
            return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String resolveBearerTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
