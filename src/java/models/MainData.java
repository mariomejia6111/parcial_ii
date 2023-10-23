package models;
import java.util.List;
public class MainData {
    private int userCount, teamCount, playerCount;
    private List<User> users;
    private List<Team> teams;
    private List<Player> players;
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
    public int getPlayerCount() {
        return playerCount;
    }
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}