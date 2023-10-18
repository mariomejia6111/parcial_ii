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
@WebServlet(name = "UpdateUser", urlPatterns = {"/UpdateUser"})
public class UpdateUser extends HttpServlet {
    private final UserCt controller = new UserCt();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strId = request.getParameter("id");
        if (strId == null) {
            request.getRequestDispatcher("Users").forward(request, response);
        }
        int id = Integer.parseInt(strId);
        User user = controller.getUserById(id);
        request.setAttribute("user", user);
        request.setAttribute("action", true);
        request.getRequestDispatcher("user_form.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            String route = "";
            if (currentUser.getId() == user.getId()) {
                HttpSession formerSession = request.getSession(false);
                formerSession.invalidate();
                request.setAttribute("alert-type", "success");
                request.setAttribute("alert-title", "¡Éxito!");
                request.setAttribute("alert-icon", "check");
                request.setAttribute("alert-message", "Por favor, Inicia sesión de nuevo");
                route = "/";
            } else {
                request.setAttribute("alert-type", "success");
                request.setAttribute("alert-title", "¡Éxito!");
                request.setAttribute("alert-icon", "check");
                request.setAttribute("alert-message", "El usuario ha sido actualizado");
                route = "Users";
            }
            controller.update(user);
            request.getRequestDispatcher(route).forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            request.setAttribute("alert-type", "danger");
            request.setAttribute("alert-title", "Error...");
            request.setAttribute("alert-icon", "ban");
            request.setAttribute("alert-message", "No pudo actualizar el usuario");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}