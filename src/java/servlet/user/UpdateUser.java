package servlet.user;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import controllers.UserCt;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import tools.RequestDelegation;
import models.Pair;
@WebServlet(name = "UpdateUser", urlPatterns = {"/UpdateUser"})
public class UpdateUser extends HttpServlet {
    private final UserCt controller = new UserCt();
    private final RequestDelegation delegation = new RequestDelegation();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strId = request.getParameter("id");
        if (strId == null) delegation.forward("Users", request, response);
        int id = Integer.parseInt(strId);
        List<Pair<String, Object>> attrs = new ArrayList();
        attrs.add(new Pair<>("action", true));
        attrs.add(new Pair<>("user", controller.getUserById(id)));
        delegation.dataResponse("routes/user_form.jsp", attrs, request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = "success", title = "Operación Completa", icon = "check", message = "Se actualizó el usuario", route = "Users";
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = controller.getUserById(id);
            User currentUser = (User)request.getSession().getAttribute("logged");
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            if (currentUser.getId() == user.getId()) {
                HttpSession formerSession = request.getSession(false);
                formerSession.invalidate();
                message = message + "pero debees iniciar sesión";
                route = "/";
            }
            controller.update(user);
            request.getRequestDispatcher(route).forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            type = "danger"; title = "Error..."; icon = "ban"; message = "No pudo actualizarse el usuario"; route = "routes/user_form.jsp";
        }
        delegation.operationResponse(route, type, title, icon, message, request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}