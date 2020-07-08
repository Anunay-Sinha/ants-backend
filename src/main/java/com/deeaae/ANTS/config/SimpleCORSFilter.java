package com.deeaae.ANTS.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleCORSFilter implements Filter {
  @Override
  public void doFilter(
      ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST,PUT, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");

    List<String> allowedHeaders = new ArrayList<>();
    //allowedHeaders.add("X-Correlation-Id");
    //allowedHeaders.add("X-Tenant");
    allowedHeaders.add("Content-Type");
    allowedHeaders.add("Accept");
    //allowedHeaders.add("X-Requested-With");
    allowedHeaders.add("Origin");
    allowedHeaders.add("Authorization");
    String headers = String.join(",", allowedHeaders);
    response.setHeader("Access-Control-Allow-Headers", headers);
    chain.doFilter(req, res);
  }

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void destroy() {
  }

}
