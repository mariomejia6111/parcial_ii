package servlet.player;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import controllers.PlayerCt;
import jakarta.servlet.http.Part;
import models.Player;
import java.io.File;
import java.nio.file.Paths;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import models.Team;
import controllers.TeamCt;
import tools.RequestDelegation;
@MultipartConfig
@WebServlet(name = "NewPlayer", urlPatterns = {"/NewPlayer"})
public class NewPlayer extends HttpServlet {
    private final PlayerCt controller = new PlayerCt();
    private final RequestDelegation delegation = new RequestDelegation();
    private final TeamCt teamCt = new TeamCt();
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
        String type = "success", title = "¡Éxito!", icon = "check", message = "El jugador ha sido registrado", route = "Players";
        Player player = new Player();
        Team team = teamCt.getTeamById(Integer.parseInt(request.getParameter("player-team")));
        String playerName = request.getParameter("player-name"), playerSurname = request.getParameter("player-surname");
        Part playerPart = request.getPart("player-photo");
        String photoFileName = getPlayerPhotoName(playerName, playerSurname, playerPart);
        player.setFirstName(playerName);
        player.setLastName(playerSurname);
        player.setPosition(request.getParameter("player-position"));
        player.setJerseyNumber(Integer.parseInt(request.getParameter("player-jersey-number")));
        player.setSpeed(Integer.parseInt(request.getParameter("player-speed")));
        player.setStrength(Integer.parseInt(request.getParameter("player-strength")));
        player.setEndurance(Integer.parseInt(request.getParameter("player-endurance")));
        player.setDribble(Integer.parseInt(request.getParameter("player-dribble")));
        player.setControl(request.getParameter("player-control"));
        player.setTeam(team);
        player.setPhoto("/players/" + photoFileName);
        try {
            controller.add(player);
            File playersDir = new File(getServletContext().getRealPath("/players"));
            if (!playersDir.exists()) playersDir.mkdirs();
            File playerPhoto = new File(playersDir, photoFileName);
            InputStream photoInput = playerPart.getInputStream();
            Files.copy(photoInput, playerPhoto.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            type = "danger"; title = "Error..."; icon = "ban"; message = "No se registró el jugador"; route = "routes/player_form.jsp";
        }
        delegation.operationResponse(route, type, title, icon, message, request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    private String getPlayerPhotoName(String name, String surname, Part photo) {
        String fileName = Paths.get(photo.getSubmittedFileName()).getFileName().toString();
        int extension = fileName.lastIndexOf(".");
        String fileExtension = (extension > 0) ? fileName.substring(extension) : "";
        String photoFilename = name.toLowerCase().replace(" ", "_") + "_" + surname.toLowerCase().replace(" ", "") + fileExtension;
        return photoFilename;
    }
}