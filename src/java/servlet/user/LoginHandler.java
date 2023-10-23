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
import java.util.ArrayList;
import java.util.List;
import models.Pair;
import tools.RequestDelegation;
@WebServlet(name = "LoginHandler", urlPatterns = {"/LoginHandler"})
public class LoginHandler extends HttpServlet {
    private final UserCt controller = new UserCt();
    private final RequestDelegation delegation = new RequestDelegation();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            delegation.operationResponse("/", "success", "Sesión Completa", "check", "Has cerrado sesión", request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = controller.getUserByUsername(username);
        if (user == null || (!user.getUsername().equals(username) && !user.getPassword().equals(password))) {
            delegation.operationResponse("routes/", "danger", "Credenciales Incorrectas", "ban", "Por favor, revisa las credenciales", request, response);
        } else {
            List<Pair<String, Object>> attrs = new ArrayList();
            attrs.add(new Pair<>("logged", user));
            delegation.dataResponse("routes/", attrs, request, response);
            request.getSession().setAttribute("logged", user);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}