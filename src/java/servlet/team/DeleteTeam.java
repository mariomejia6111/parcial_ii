package servlet.team;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import controllers.TeamCt;
import java.io.File;
import tools.RequestDelegation;
@WebServlet(name = "DeleteTeam", urlPatterns = {"/DeleteTeam"})
public class DeleteTeam extends HttpServlet {
    private final TeamCt controller = new TeamCt();
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
        int id = Integer.parseInt(request.getParameter("id"));
        String img = request.getParameter("img");
        File imgFile = new File(getServletContext().getRealPath(img));
        if (imgFile.exists()) {
            imgFile.delete();
        }
        controller.delete(id);
        delegation.operationResponse("Teams", "success", "Eliminación Completa", "check", "Se eliminó el equipo", request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}