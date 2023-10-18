package servlet.team;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.File;
import java.nio.file.Paths;
import models.Team;
import controllers.TeamCt;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
@WebServlet(name = "NewTeam", urlPatterns = {"/NewTeam"})
@MultipartConfig
public class NewTeam extends HttpServlet {
    private final TeamCt controller = new TeamCt();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String teamName = request.getParameter("team-name");
            Part teamImgPart = request.getPart("team-img");
            String fileName = Paths.get(teamImgPart.getSubmittedFileName()).getFileName().toString();
            int extension = fileName.lastIndexOf(".");
            String fileExtension = (extension > 0) ? fileName.substring(extension) : "";
            String imgFilename = teamName.toLowerCase().replace(" ", "_") + fileExtension;
            String teamsDirPath = getServletContext().getRealPath("/teams");
            File teamsDir = new File(teamsDirPath);
            if (!teamsDir.exists()) {
                teamsDir.mkdirs();
            }
            File teamImg = new File(teamsDir, imgFilename);
            InputStream imgInput = teamImgPart.getInputStream();
            Files.copy(imgInput, teamImg.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Team team = new Team();
            team.setTeamName(teamName);
            team.setTeamImg("/teams/" + imgFilename);
            controller.add(team);
            request.setAttribute("alert-type", "success");
            request.setAttribute("alert-title", "¡Éxito!");
            request.setAttribute("alert-icon", "check");
            request.setAttribute("alert-message", "El equipo fue registrado");
            request.getRequestDispatcher("Teams").forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            request.setAttribute("alert-type", "danger");
            request.setAttribute("alert-title", "Error...");
            request.setAttribute("alert-icon", "ban");
            request.setAttribute("alert-message", "No pudo registrarse el equipo");
            request.getRequestDispatcher("Teams").forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}