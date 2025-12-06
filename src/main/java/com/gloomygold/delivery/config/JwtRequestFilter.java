package com.gloomygold.delivery.config;

import com.gloomygold.delivery.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.FirebaseApp;


import java.io.IOException;
import java.util.Collections; // Import Collections for empty authorities list

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final FirebaseApp firebaseApp;


    public JwtRequestFilter(UserService userService, JwtUtil jwtUtil, FirebaseApp firebaseApp) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.firebaseApp = firebaseApp;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;
        boolean isFirebaseToken = false;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7);

            // Try Firebase token verification first
            try {
                FirebaseToken firebaseToken = FirebaseAuth.getInstance(firebaseApp).verifyIdToken(token);
                username = firebaseToken.getEmail(); // Use email as username for Firebase users
                isFirebaseToken = true;
                logger.info("Firebase token successfully verified for user: " + username);
            } catch (FirebaseAuthException e) {
                logger.warn("Firebase token verification failed or token is not a Firebase ID token: " + e.getMessage());
                // Fallback to application-specific JWT verification
            }

            // If not a Firebase token or Firebase verification failed, try application-specific JWT
            if (!isFirebaseToken) {
                try {
                    username = jwtUtil.getUsernameFromToken(token);
                    logger.info("Application JWT successfully verified for user: " + username);
                } catch (IllegalArgumentException e) {
                    logger.warn("Unable to get JWT Token: " + e.getMessage());
                } catch (ExpiredJwtException e) {
                    logger.warn("JWT Token has expired: " + e.getMessage());
                }
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // Once we have a username (from either Firebase or app JWT), set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;

            if (isFirebaseToken) {
                // For Firebase users, you might want to create a UserDetails object on the fly
                // or load from your database if you store Firebase user info.
                // For now, let's create a simple UserDetails object.
                userDetails = new org.springframework.security.core.userdetails.User(username, "", Collections.emptyList());
            } else {
                userDetails = this.userService.loadUserByUsername(username);
            }


            // Validate application JWT only if it's not a Firebase token
            if (isFirebaseToken || jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
