package org.example;

import org.jetbrains.annotations.NotNull;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String timezone = request.getParameter("timezone");
        if (timezone == null || timezone.isEmpty()) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastTimezone".equals(cookie.getName())) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }
        }
        if (timezone == null || timezone.isEmpty()) {
            timezone = "UTC";
        }

        ZoneId zoneId = ZoneId.of(timezone);

        Cookie timezoneCookie = new Cookie("lastTimezone", timezone);
        timezoneCookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(timezoneCookie);

        Instant now = Instant.now();
        String formattedTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(zoneId)
                .format(now);

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        WebContext context = new WebContext(request.getLocale(), request.getServletContext());
        context.setVariable("timezone", zoneId.toString());
        context.setVariable("time", formattedTime);

        templateEngine.process("time", context, response.getWriter());
    }
}
