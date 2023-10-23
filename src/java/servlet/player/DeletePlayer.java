package servlet.player;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import controllers.PlayerCt;
import tools.RequestDelegation;
import java.io.File;
@WebServlet(name = "DeletePlayer", urlPatterns = {"/DeletePlayer"})
public class DeletePlayer extends HttpServlet {
    private final PlayerCt controller = new PlayerCt();
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
        File photo = new File(getServletContext().getRealPath(img));
        if (photo.exists()) photo.delete();
        controller.delete(id);
        delegation.operationResponse("Players", "success", "Eliminación Completa", "check", "Se eliminó el jugador", request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}