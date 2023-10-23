package servlet.user;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import controllers.UserCt;
import java.util.ArrayList;
import java.util.List;
import tools.RequestDelegation;
import models.Pair;
@WebServlet(name = "Users", urlPatterns = {"/Users"})
public class Users extends HttpServlet {
    private final UserCt controller = new UserCt();
    private final RequestDelegation delegation = new RequestDelegation();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*HttpSession authSession = request.getSession(false);
        if (authSession == null || authSession.getAttribute("logged") == null) {
            request.getRequestDispatcher("/").forward(request, response);
        }*/
        List<Pair<String, Object>> attrs = new ArrayList();
        attrs.add(new Pair<>("users", controller.getUsers()));
        delegation.dataResponse("routes/users.jsp", attrs, request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}