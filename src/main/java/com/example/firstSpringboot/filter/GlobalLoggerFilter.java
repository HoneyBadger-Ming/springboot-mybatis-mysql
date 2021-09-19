package com.example.firstSpringboot.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class GlobalLoggerFilter extends OncePerRequestFilter {

    private static final Log LOGGER = LogFactory.getLog(GlobalLoggerFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("==============================================Start=================================================");
        LOGGER.info(String.format("URL: %s.", httpServletRequest.getRequestURI()));
        LOGGER.info(String.format("HTTP Method: %s.", httpServletRequest.getMethod()));
        LOGGER.info(String.format("IP: %s.", httpServletRequest.getRemoteAddr()));
        LOGGER.info(String.format("Request Header: %s.", httpServletRequest.getHeaderNames()));
        LOGGER.info(String.format("Request Body: %s.", httpServletRequest.getContextPath()));
        filterChain.doFilter(httpServletRequest,httpServletResponse);
        LOGGER.info("==============================================End=================================================");
        LOGGER.info(String.format("Response Body: %s.", httpServletResponse.getStatus()));
    }
}
