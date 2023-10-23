package servlet.user;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import controllers.UserCt;
import tools.RequestDelegation;
@WebServlet(name = "NewUser", urlPatterns = {"/NewUser"})
public class NewUser extends HttpServlet {
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
        String type = "success", title = "Operación Finalizada", icon = "check", message = "Se ingresó el usuario", route = "Users";
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
                request.getRequestDispatcher("routes/").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            type = "danger"; title = "Error..."; icon = "ban"; message = "No se pudo registrar el usuario"; route = "routes/user_form.jsp";
        }
        delegation.operationResponse(route, type, title, icon, message, request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}