package tools;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import models.Pair;
public class RequestDelegation {
    public void forward(String route, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(route).forward(req, res);
    }
    public void home(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        forward("routes/", req, res);
    }
    public void operationResponse(String route, String type, String title, String icon, String message, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("alert-type", type);
        req.setAttribute("alert-title", title);
        req.setAttribute("alert-icon", icon);
        req.setAttribute("alert-message", message);
        forward(route, req, res);
    }
    public void dataResponse(String route, List<Pair<String, Object>> attrs, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        for (Pair<String, Object> attr: attrs) {
            req.setAttribute(attr.getT(), attr.getU());
        }
        forward(route, req, res);
    }
}