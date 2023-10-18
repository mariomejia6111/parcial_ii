package servlet.user;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import controllers.UserCt;
@WebServlet(name = "NewUser", urlPatterns = {"/NewUser"})
public class NewUser extends HttpServlet {
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
        try {
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String startAuth = request.getParameter("get-auth");
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            controller.add(user);
            if (startAuth != null) {
                request.getSession().setAttribute("logged", user);
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            }
            request.setAttribute("alert-type", "success");
            request.setAttribute("alert-title", "¡Éxito!");
            request.setAttribute("alert-icon", "check");
            request.setAttribute("alert-message", "El usuario ha sido insertado");
            request.getRequestDispatcher("Users").forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            request.setAttribute("alert-type", "danger");
            request.setAttribute("alert-title", "Error...");
            request.setAttribute("alert-icon", "ban");
            request.setAttribute("alert-message", "No pudo insertarse el usuario");
            request.getRequestDispatcher("user_form.jsp").forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}