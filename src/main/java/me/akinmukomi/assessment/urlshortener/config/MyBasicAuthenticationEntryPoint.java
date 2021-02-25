package me.akinmukomi.assessment.urlshortener.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public final class MyBasicAuthenticationEntryPoint implements AuthenticationEntryPoint{

	 @Override
	 public void commence(
			 HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) 
		     throws IOException, ServletException {
		     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		     PrintWriter writer = response.getWriter();
		     writer.println("HTTP Status 401 - " + authEx.getMessage());
	}
}
