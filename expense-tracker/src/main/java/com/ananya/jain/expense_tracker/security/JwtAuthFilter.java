package com.ananya.jain.expense_tracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(Objects.isNull(header) || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            // Skipping JWT processing and continue filter chain
            return ;
        }

        String token = header.substring(7);

        String email = jwtService.extractEmail(token);

        if(Objects.nonNull(email) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())){

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            boolean isValid = jwtService.isTokenValid(token, email);

            if(isValid){
                //creating authentication object
                //Spring security's default login filter
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
