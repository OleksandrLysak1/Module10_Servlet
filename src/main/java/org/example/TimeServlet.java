package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        String timezone = request.getParameter("timezone");
        ZoneId zoneId;

        if (timezone == null || timezone.isEmpty()) {
            zoneId = ZoneId.of("UTC");
        } else {
            zoneId = ZoneId.of(timezone);  // Валідація через фільтр
        }

        Instant now = Instant.now();
        String formattedTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(zoneId)
                .format(now);

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Current Time</title></head>");
        out.println("<body>");
        out.println("<h1>Current Time in " + zoneId + "</h1>");
        out.println("<p>" + formattedTime + " " + zoneId + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}
