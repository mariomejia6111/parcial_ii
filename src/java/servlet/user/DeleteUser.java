package servlet.user;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import controllers.UserCt;
import jakarta.servlet.http.HttpSession;
import models.User;
@WebServlet(name = "DeleteUser", urlPatterns = {"/DeleteUser"})
public class DeleteUser extends HttpServlet {
    private final UserCt controller = new UserCt();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Users").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User currentUser = (User)request.getSession().getAttribute("logged");
        String route = "";
        if (currentUser.getId() == id) {
            HttpSession formerSession = request.getSession(false);
            formerSession.invalidate();
            request.setAttribute("alert-type", "success");
            request.setAttribute("alert-title", "¡Éxito!");
            request.setAttribute("alert-icon", "check");
            request.setAttribute("alert-message", "Has eliminado tu usuario");
            route = "/";
        } else {
            route = "Users";
        }
        controller.delete(id);
        request.getRequestDispatcher(route).forward(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}