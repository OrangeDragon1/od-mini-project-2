package vttp2022.batch2a.miniproject2.server.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

  private static final String SECRET_KEY = "2D4A614E645267556A586E3272357538782F413F4428472B4B6250655368566D";
  private static final Long TOKEN_EXPIRY = TimeUnit.HOURS.toMillis(2); // 2 HOURS

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String extractUsername() {
    return extractClaim(getCurrentToken(), Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String extractRole() {
    return (String) extractClaim(getCurrentToken(), claims -> claims.get("role"));
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  } 

  public String generateToken(String role, UserDetails userDetails) {
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("role", role);
    return generateToken(extraClaims, userDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRY))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public Boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private Boolean isTokenExpired(String token) {
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
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private String getCurrentToken() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();

    if (null == attributes) {
      return null;
    }

    String authHeader = attributes.getRequest().getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }

    return authHeader.substring(7);
  }

}
