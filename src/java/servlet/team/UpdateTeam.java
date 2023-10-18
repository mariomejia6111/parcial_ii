package servlet.team;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Team;
import controllers.TeamCt;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@WebServlet(name = "UpdateTeam", urlPatterns = {"/UpdateTeam"})
@MultipartConfig
public class UpdateTeam extends HttpServlet {
    private final TeamCt controller = new TeamCt();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strId = request.getParameter("id");
        if (strId == null) {
            request.getRequestDispatcher("Teams").forward(request, response);
        }
        int id = Integer.parseInt(strId);
        Team team = controller.getTeamById(id);
        request.setAttribute("team", team);
        request.setAttribute("action", true);
        request.getRequestDispatcher("team_form.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt((String)request.getParameter("id"));
            String teamName = request.getParameter("team-name");
            Part teamImgPart = request.getPart("team-img");
            Team updatingTeam = controller.getTeamById(id);
            if (teamImgPart.getSubmittedFileName().equals("")) {
                if (!teamName.equals(updatingTeam.getTeamName())) {
                    File updatingImg = getImg(updatingTeam.getTeamImg().replace("/teams", ""));
                    if (updatingImg.exists()) {
                        Path formerImg = Paths.get(getServletContext().getRealPath("/teams"), updatingImg.getName());
                        Path updatedImg = Paths.get(getServletContext().getRealPath("/teams"), teamName.toLowerCase().replace(" ", "_") + updatingTeam.getTeamImg().substring(updatingTeam.getTeamImg().lastIndexOf(".")));
                        Files.move(formerImg, updatedImg);
                        updatingTeam.setTeamName(teamName);
                        updatingTeam.setTeamImg("/teams/" + updatedImg.getFileName().toString());
                    }
                }
            } else {
                File formerImg = getImg(getImgFileName(updatingTeam.getTeamImg(), updatingTeam.getTeamName()));
                if (formerImg.exists()) formerImg.delete();
                String newImgName = getImgFileName(teamImgPart.getSubmittedFileName(), teamName);
                File newImg = getImg(newImgName);
                copyImg(teamImgPart.getInputStream(), newImg);
                updatingTeam.setTeamName(teamName);
                updatingTeam.setTeamImg("/teams/" + newImgName);
            }
            controller.update(updatingTeam);
            request.setAttribute("alert-type", "success");
            request.setAttribute("alert-title", "¡Éxito!");
            request.setAttribute("alert-icon", "check");
            request.setAttribute("alert-message", "El equipo se actualizó");
            request.getRequestDispatcher("Teams").forward(request, response);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            request.setAttribute("alert-type", "danger");
            request.setAttribute("alert-title", "¡Ooops!");
            request.setAttribute("alert-icon", "ban");
            request.setAttribute("alert-message", "El equipo no fue actualizado");
            request.getRequestDispatcher("Teams").forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    private File getImg(String imgFileName) {
        String teamsDirPath = getServletContext().getRealPath("/teams");
        File teamsDir = new File(teamsDirPath);
        if (!teamsDir.exists()) {
            teamsDir.mkdirs();
        }
        return new File(teamsDir, imgFileName);
    }
    private String getImgFileName(String path, String name) {
        String fileName = Paths.get(path).getFileName().toString();
        int extension = fileName.lastIndexOf(".");
        String fileExtension = (extension > 0) ? fileName.substring(extension) : "";
        String imgFileName = name.toLowerCase().replace(" ", "_") + fileExtension;
        return imgFileName;
    }
    private void copyImg(InputStream stream, File img) {
        try {
            InputStream imgInput = stream;
            Files.copy(imgInput, img.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}