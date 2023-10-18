package models;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Team implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;
    private String teamName, teamImg;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getTeamImg() {
        return teamImg;
    }
    public void setTeamImg(String teamImg) {
        this.teamImg = teamImg;
    }
}