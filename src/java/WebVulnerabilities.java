package java;

import javax.servlet.http.*;
import java.io.*;

/**
 * This class contains web vulnerabilities (XSS, etc.).
 * Used for testing SAST tools.
 */
public class WebVulnerabilities extends HttpServlet {

    /**
     * Reflected XSS vulnerability
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        // VULNERABLE: Direct output of user input
        String name = req.getParameter("name");
        resp.getWriter().println("<h1>Hello " + name + "</h1>");
    }

    /**
     * Stored XSS vulnerability simulation
     */
    public String displayComment(String userComment) {
        // VULNERABLE: No output encoding
        return "<div class='comment'>" + userComment + "</div>";
    }

    /**
     * HTTP Response Splitting vulnerability
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        // VULNERABLE: User input in HTTP headers
        String location = req.getParameter("redirect");
        resp.setHeader("Location", location);
        resp.sendRedirect(location);
    }

    /**
     * Open Redirect vulnerability
     */
    public void redirectUser(HttpServletResponse resp, String url)
            throws IOException {
        // VULNERABLE: No validation of redirect URL
        resp.sendRedirect(url);
    }

    /**
     * Insecure cookie handling
     */
    protected void setCookies(HttpServletResponse resp, String userId) {
        // VULNERABLE: Cookie without HttpOnly flag
        Cookie cookie = new Cookie("sessionId", userId);
        cookie.setMaxAge(3600);
        // VULNERABLE: Missing secure flag in production
        resp.addCookie(cookie);
    }

    /**
     * Safe XSS prevention with output encoding
     */
    public String safeDisplayComment(String userComment) {
        // SAFE: Encode HTML special characters
        String encoded = userComment
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
        return "<div class='comment'>" + encoded + "</div>";
    }
}