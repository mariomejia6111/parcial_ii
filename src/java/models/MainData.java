package models;
import java.util.List;
public class MainData {
    private int userCount;
    private int teamCount;
    private List<User> users;
    private List<Team> teams;
    public int getUserCount() {
        return userCount;
    }
    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
    public int getTeamCount() {
        return teamCount;
    }
    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    public List<Team> getTeams() {
        return teams;
    }
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}