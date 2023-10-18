package servlet.user;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import controllers.UserCt;
import jakarta.servlet.http.HttpSession;
@WebServlet(name = "Users", urlPatterns = {"/Users"})
public class Users extends HttpServlet {
    private final UserCt controller = new UserCt();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession authSession = request.getSession(false);
        if (authSession == null || authSession.getAttribute("logged") == null) {
            request.getRequestDispatcher("/").forward(request, response);
        }
        request.setAttribute("users", controller.getUsers());
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession authSession = request.getSession(false);
        if (authSession == null || authSession.getAttribute("logged") == null) {
            request.getRequestDispatcher("/").forward(request, response);
        }
        request.setAttribute("users", controller.getUsers());
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}