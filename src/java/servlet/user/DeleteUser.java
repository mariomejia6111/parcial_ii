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
import tools.RequestDelegation;
@WebServlet(name = "DeleteUser", urlPatterns = {"/DeleteUser"})
public class DeleteUser extends HttpServlet {
    private final UserCt controller = new UserCt();
    private final RequestDelegation delegation = new RequestDelegation();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        delegation.home(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = "success", title = "Operación Completa", icon = "check", message = "Se eliminó el usuario", route = "Users";
        int id = Integer.parseInt(request.getParameter("id"));
        User currentUser = (User)request.getSession().getAttribute("logged");
        if (currentUser.getId() == id) {
            HttpSession formerSession = request.getSession(false);
            formerSession.invalidate();
            message = message + ", pero debes iniciar sesión";
            route = "/";
        }
        controller.delete(id);
        delegation.operationResponse(route, type, title, icon, message, request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}