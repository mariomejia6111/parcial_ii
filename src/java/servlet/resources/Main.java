package servlet.resources;
import com.google.gson.Gson;
import controllers.TeamCt;
import controllers.UserCt;
import controllers.PlayerCt;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import models.MainData;
@WebServlet(name = "Main", urlPatterns = {"/Main"})
public class Main extends HttpServlet {
    private final UserCt userCt = new UserCt();
    private final TeamCt teamCt = new TeamCt();
    private final PlayerCt playerCt = new PlayerCt();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        new tools.RequestDelegation().home(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        MainData mainData = new MainData();
        Gson gson = new Gson();
        mainData.setTeamCount(teamCt.getTeamsAmount());
        mainData.setUserCount(userCt.getUsersAmount());
        mainData.setPlayerCount(playerCt.getPlayersAmount());
        mainData.setTeams(teamCt.getSomeTeams());
        mainData.setUsers(userCt.getSomeUsers());
        mainData.setPlayers(playerCt.getSomePlayers());
        String data = gson.toJson(mainData);
        PrintWriter out = response.getWriter();
        out.print(data);
        out.flush();
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