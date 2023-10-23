package servlet.player;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import models.Player;
import models.Team;
import controllers.PlayerCt;
import controllers.TeamCt;
import java.util.ArrayList;
import java.util.List;
import tools.RequestDelegation;
import models.Pair;
@WebServlet(name = "UpdatePlayer", urlPatterns = {"/UpdatePlayer"})
@MultipartConfig
public class UpdatePlayer extends HttpServlet {
    private final PlayerCt controller = new PlayerCt();
    private final TeamCt teamCt = new TeamCt();
    private final RequestDelegation delegation = new RequestDelegation();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strId = request.getParameter("id");
        if (strId == null) delegation.forward("Players", request, response);
        int id = Integer.parseInt(strId);
        List<Pair<String, Object>> attrs = new ArrayList();
        attrs.add(new Pair<>("action", true));
        attrs.add(new Pair<>("player", controller.getPlayerById(id)));
        delegation.dataResponse("routes/player_form.jsp", attrs, request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = "success", title = "¡Éxito!", icon = "check", message = "Se actualizó la información", route = "Players";
        int id = Integer.parseInt(request.getParameter("id"));
        String playerName = request.getParameter("player-name"), playerSurname = request.getParameter("player-surname");
        Player player = controller.getPlayerById(id);
        Part photoPart = request.getPart("player-photo");
        String dir = getServletContext().getRealPath("/players");
        Team team = teamCt.getTeamById(Integer.parseInt(request.getParameter("player-team")));
        if (photoPart.getSubmittedFileName().equals("")) {
            if (!playerName.equals(player.getFirstName()) || !playerSurname.equals(player.getLastName())) {
                File updatingPhoto = new File(dir, player.getPhoto().replace("/players", ""));
                if (updatingPhoto.exists()) {
                    Path formerPath = Paths.get(dir, updatingPhoto.getName());
                    String filename = playerName.toLowerCase().replace(" ", "_") + "_" + playerSurname.toLowerCase().replace(" ", "_") + player.getPhoto().substring(player.getPhoto().lastIndexOf("."));
                    Path newPhotoPath = Paths.get(dir, filename);
                    Files.move(formerPath, newPhotoPath);
                    player.setPhoto("/players/" + newPhotoPath.getFileName().toString());
                }
            }
        } else {
            File formerPhoto = new File(dir, player.getPhoto().replace("/players", ""));
            if (formerPhoto.exists()) formerPhoto.delete();
            String newPhotoName = getPlayerPhotoName(playerName, playerSurname, photoPart);
            File newPhoto = new File(dir, newPhotoName);
            copyImg(photoPart.getInputStream(), newPhoto);
            player.setPhoto("/players/" + newPhotoName);
        }
        player.setTeam(team);
        player.setFirstName(playerName);
        player.setLastName(playerSurname);
        player.setPosition(request.getParameter("player-position"));
        player.setJerseyNumber(Integer.parseInt(request.getParameter("player-jersey-number")));
        player.setSpeed(Integer.parseInt(request.getParameter("player-speed")));
        player.setStrength(Integer.parseInt(request.getParameter("player-strength")));
        player.setEndurance(Integer.parseInt(request.getParameter("player-endurance")));
        player.setDribble(Integer.parseInt(request.getParameter("player-dribble")));
        player.setControl(request.getParameter("player-control"));
        controller.update(player);
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
    private void copyImg(InputStream stream, File img) {
        try {
            InputStream imgInput = stream;
            Files.copy(imgInput, img.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}