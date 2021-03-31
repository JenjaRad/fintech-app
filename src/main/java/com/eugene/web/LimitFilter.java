package com.eugene.web;

import com.eugene.service.LimitService;
import com.eugene.web.forms.FailError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Locale;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class LimitFilter implements Filter {
    private Logger logger = getLogger(LimitFilter.class);
    private LimitService limitService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public LimitFilter(LimitService limitService) {
        this.limitService = limitService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Get filter name",filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("filter",servletRequest,servletResponse,filterChain);
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
