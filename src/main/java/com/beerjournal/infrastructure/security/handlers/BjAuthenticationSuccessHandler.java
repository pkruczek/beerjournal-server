package com.beerjournal.infrastructure.security.handlers;

import com.beerjournal.infrastructure.security.BjUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BjAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication auth) throws IOException, ServletException {

        BjUser user = (BjUser) auth.getPrincipal();
        String responseBody = user.getDbUser().getId().toHexString();
        saveBody(response.getWriter(), responseBody);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void saveBody(PrintWriter writer, String responseBody) {
        writer.write(responseBody);
        writer.flush();
        writer.close();
    }
}
