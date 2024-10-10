package com.pokemon.pokemon.security;

import com.pokemon.pokemon.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    private UserService userService;
    private JwtService jwtService;

    public JwtFilter(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        Boolean isTokenExpired = true;

        final String authorization = request.getHeader("Authorization");
        // --> Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjgzMjAzODIsImV4cCI6MTcyODMyMjE4Miwic3ViIjoiYkBiLmNvbSIsIm5vbSI6IkJlcnRoZWxvdCIsInByZW5vbSI6Ik1hdHRoaWFzIiwiZW1haWwiOiJiQGIuY29tIn0.l2ihmuS5rlzvqr8xtRiOrTq_KiFIRrnX5G0Rl--NJxQ

        if(authorization != null && authorization.startsWith("Bearer ")){
            // Ici on récupère uniquement le token en "supprimant" le "Bearer "
            token = authorization.substring(7);
            isTokenExpired = jwtService.isTokenExpired(token);
            username = jwtService.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && !isTokenExpired) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
