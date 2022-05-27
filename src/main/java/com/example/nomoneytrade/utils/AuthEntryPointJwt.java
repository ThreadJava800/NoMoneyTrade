package com.example.nomoneytrade.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


/*
    Handles authentication exceptions
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // setting empty content and error status
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // tell user that he has lost
        HashMap<String, Object> returnData = new HashMap<>();
        returnData.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        returnData.put("error", "Unauthorized");
        returnData.put("message", authException.getMessage());
        returnData.put("path", request.getServletPath());

        // serializer
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), returnData);
    }
}
