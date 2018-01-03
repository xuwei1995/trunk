package com.serviceindeed.yike.yikemo.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class IfkCorsFilter implements Filter {

   @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
       response.setHeader("Access-Control-Allow-Origin", "*");
       response.setHeader("Access-Control-Allow-Methods", "*");
       response.setHeader("Access-Control-Max-Age", "3600");
      // response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header ");
       response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, x-requested-with, X-Custom-Header,*");
       chain.doFilter(req, res);
   }
    @Override
   public void init(FilterConfig filterConfig) {
   }

   @Override
   public void destroy() {
    }
}
