package com.gustavoswdanioel.inventory_manager.infrastructure.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.Filter;
import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                         jakarta.servlet.ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        long startTime = System.currentTimeMillis();

        logger.info("Request: {} {} - Query: {}",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                httpRequest.getQueryString());

        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;

        logger.info("Response: {} {} - Status: {} - Duration: {} ms",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                httpResponse.getStatus(),
                duration);
    }
}
