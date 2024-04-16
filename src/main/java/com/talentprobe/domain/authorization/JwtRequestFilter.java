package com.talentprobe.domain.authorization;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Value("${jwt.publicKeyPath}")
  private String publicKeyPath;


  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws IOException {

    try {
      String token = decodeToken(httpServletRequest);
      if (null != token) {
        PublicKey publicKey = getPublicKey(publicKeyPath);

        Claims claims = Jwts.parser().setSigningKey(publicKey).build().parseSignedClaims(token)
            .getPayload();
        String userId = (String) claims.get("user_id");
        if (null != userId) {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              userId, null, null);
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
      filterChain.doFilter(httpServletRequest, httpServletResponse);
    } catch (ExpiredJwtException expiredJwtException) {
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write("Token Expired");
    } catch (MalformedJwtException | UnsupportedJwtException exception) {
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write("Token Invalid");
    } catch (Exception exception) {
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write("Unauthorized");
    }
  }

  private PublicKey getPublicKey(String publicKeyPath) {
    PublicKey publicKey = null;
    Resource resource = new ClassPathResource(publicKeyPath);
    try (BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()))) {
      StringBuilder pemContent = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("-----BEGIN PUBLIC KEY-----") || line.startsWith(
            "-----BEGIN RSA PUBLIC KEY-----")) {
          continue;
        }
        if (line.startsWith("-----END PUBLIC KEY-----") || line.startsWith(
            "-----END RSA PUBLIC KEY-----")) {
          continue;
        }
        pemContent.append(line);
      }
      byte[] decodedKey = Base64.getDecoder().decode(pemContent.toString());
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      publicKey = keyFactory.generatePublic(keySpec);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return publicKey;
  }

  private String decodeToken(HttpServletRequest httpServletRequest) {
    String bearerToken = httpServletRequest.getHeader("Authorization");
    if (null != bearerToken) {
      if (bearerToken.startsWith("Bearer ")) {
        return bearerToken.substring(7);
      } else {
        return bearerToken;
      }
    }
    return null;
  }
}
