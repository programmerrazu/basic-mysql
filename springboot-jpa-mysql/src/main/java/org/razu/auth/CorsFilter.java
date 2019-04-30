package org.razu.auth;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CorsFilter implements Filter {

    @Value(value = "${app.security.cors.headers:Authorization, Content-Type, Accept, x-requested-with, Cache-Control}")
    private String corsHeaders;

    @Value(value = "${app.security.cors.methods:POST, GET, OPTIONS, DELETE, PUT}")
    private String corsMethods;

    @Value(value = "${app.security.cors.origin:*}")
    private String corsOrigin;

    @Value(value = "${app.security.cors.max-age:3600}")
    private String corsMaxAge;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", corsOrigin);
        res.setHeader("Access-Control-Allow-Methods", corsMethods);
        res.setHeader("Access-Control-Max-Age", corsMaxAge);
        // res.setHeader("X-Frame-Options", "SAMEORIGIN");
        res.setHeader("Access-Control-Allow-Headers", corsHeaders);
        chain.doFilter(request, res);
    }

    @Override
    public void destroy() {
    }
}
