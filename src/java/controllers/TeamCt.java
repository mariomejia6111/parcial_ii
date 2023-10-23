package controllers;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.TeamJpaController;
import models.Team;
import persistence.exceptions.NonexistentEntityException;
public class TeamCt {
    private final TeamJpaController controller = new TeamJpaController();
    public void add(Team team) {
        controller.create(team);
    }
    public List<Team> getTeams() {
        return controller.findTeamEntities();
    }
    public List<Object[]> getAvailableTeams() {
        return controller.getAvailableTeams();
    }
    public Team getTeamById(int id) {
        return controller.findTeam(id);
    }
    public void update(Team team) {
        try {
            controller.edit(team);
        } catch (Exception ex) {
            Logger.getLogger(TeamCt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete(int id) {
        try {
            controller.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TeamCt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getTeamsAmount() {
        return controller.getTeamCount();
    }
    public List<Team> getSomeTeams() {
        return controller.findTeamEntities(8, 0);
    }
}