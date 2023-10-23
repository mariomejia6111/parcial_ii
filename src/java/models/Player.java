package models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
@Entity
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName, lastName, position, photo, control;
    private int jerseyNumber, speed, strength, endurance, dribble;
    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public int getJerseyNumber() {
        return jerseyNumber;
    }
    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getControl() {
        return control;
    }
    public void setControl(String control) {
        this.control = control;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getEndurance() {
        return endurance;
    }
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }
    public int getDribble() {
        return dribble;
    }
    public void setDribble(int dribble) {
        this.dribble = dribble;
    }
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
}