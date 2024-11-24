package com.welfeki.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Log comprehensive request details
            logger.info("Request Details:");
            logger.info("URL: {}", request.getRequestURL());
            logger.info("Method: {}", request.getMethod());
            logger.info("Content Type: {}", request.getContentType());

            // Log all headers
            logger.info("Request Headers:");
            java.util.Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                logger.info("{}: {}", headerName, request.getHeader(headerName));
            }

            // Extract JWT token
            String authHeader = request.getHeader("Authorization");
            logger.info("Authorization Header: {}", authHeader);

            // If no Authorization header, continue filter chain
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("No JWT token found or incorrect format");
                filterChain.doFilter(request, response);
                return;
            }

            // Remove "Bearer " prefix
            String jwt = authHeader.substring(7);
            logger.info("Extracted JWT Token: {}", jwt);

            // Verify the token
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecParams.SECRET)).build();
            DecodedJWT decodedJWT = verifier.verify(jwt);

            // Extract claims
            String username = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);

            logger.info("Username from token: {}", username);
            logger.info("Roles from token: {}", roles);

            // Create authorities
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
                logger.info("Added Authority: {}", role);
            }

            // Create authentication token
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            // Set authentication in security context
            SecurityContextHolder.getContext().setAuthentication(authToken);
            logger.info("Authentication successful for user: {}", username);

        } catch (JWTVerificationException e) {
            logger.error("JWT Verification Failed: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT Token");
            return;
        } catch (Exception e) {
            logger.error("Unexpected error in JWT processing: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error processing authentication");
            return;
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}