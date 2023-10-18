package servlet.user;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import controllers.UserCt;
@WebServlet(name = "LoginHandler", urlPatterns = {"/LoginHandler"})
public class LoginHandler extends HttpServlet {
    private final UserCt controller = new UserCt();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            request.setAttribute("alert-type", "success");
            request.setAttribute("alert-title", "Log out");
            request.setAttribute("alert-icon", "check");
            request.setAttribute("alert-message", "Has cerrado sesión");
            request.getRequestDispatcher("/").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = controller.getUserByUsername(username);
        if (user == null || (!user.getUsername().equals(username) && !user.getPassword().equals(password))) {
            request.setAttribute("alert-type", "danger");
            request.setAttribute("alert-title", "Credenciales Incorrectas");
            request.setAttribute("alert-icon", "ban");
            request.setAttribute("alert-message", "Por favor, revisa las credenciales");
            request.getRequestDispatcher("/").forward(request, response);
        } else {
            request.getSession().setAttribute("logged", user);
            request.getRequestDispatcher("./routes/dashboard.jsp").forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}