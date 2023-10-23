package servlet.team;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import controllers.TeamCt;
import java.util.List;
import com.google.gson.Gson;
import models.Team;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import tools.RequestDelegation;
import models.Pair;
@WebServlet(name = "Teams", urlPatterns = {"/Teams"})
public class Teams extends HttpServlet {
    private final TeamCt controller = new TeamCt();
    private final RequestDelegation delegation = new RequestDelegation();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*HttpSession authSession = request.getSession(false);
        if (authSession == null || authSession.getAttribute("logged") == null) {
            request.getRequestDispatcher("/").forward(request, response);
        }*/
        if (request.getParameter("x") != null) {
            List<Object[]> dataset = controller.getAvailableTeams(); List<Team> teams = new ArrayList();
            PrintWriter out = response.getWriter(); 
            if (!dataset.isEmpty()) {
                for (Object[] item: dataset) {
                    Team team = new Team(); team.setId(Integer.parseInt(item[0].toString())); team.setTeamName(item[2].toString()); team.setTeamImg(item[1].toString()); teams.add(team);
                }
                out.print(new Gson().toJson(teams));
            } else {
                out.print(0);
            }
            out.flush();
        } else {
            List<Pair<String, Object>> attrs = new ArrayList();
            attrs.add(new Pair<>("teams", controller.getTeams()));
            delegation.dataResponse("routes/teams.jsp", attrs, request, response);
        }
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