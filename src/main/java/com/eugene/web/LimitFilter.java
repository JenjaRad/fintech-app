package com.eugene.web;

import com.eugene.service.LimitService;
import com.eugene.web.forms.FailError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Locale;

@Component
public class LimitFilter implements Filter {
    private LimitService limitService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public LimitFilter(LimitService limitService) {
        this.limitService = limitService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Locale locale = servletRequest.getLocale();
        if (!this.limitService.isLimit(locale != null ? locale.getCountry() : "ua")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            servletResponse.getOutputStream().write(objectMapper.writeValueAsBytes(new FailError("Cannot execute this country")));
        }
    }

    @Override
    public void destroy() {
    }
}
