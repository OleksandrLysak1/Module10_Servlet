package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Ініціалізація, якщо необхідно
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String timezone = httpRequest.getParameter("timezone");

        if (timezone != null && !timezone.isEmpty()) {
            try {
                ZoneId.of(timezone);
            } catch (Exception e) {
                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpResponse.setContentType("text/html");
                httpResponse.getWriter().println("<html><body><h1>Недійсна зона часу</h1></body></html>");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Очистка ресурсів, якщо необхідно
    }
}
