package com.alten.shop.utils;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AdminEmailFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = (String) authentication.getPrincipal();
            if (request.getRequestURI().startsWith("/products")
                    && ("POST".equals(request.getMethod()) || "PUT".equals(request.getMethod()) || "DELETE".equals(request.getMethod()))
                    && !"admin@admin.com".equals(email)) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
