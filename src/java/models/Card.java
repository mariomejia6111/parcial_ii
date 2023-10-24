package models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Card implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Player player;
    private Match match;
    private CardType cardType;
    // Otras propiedades relevantes

    public Card(int id, Player player, Match match, CardType cardType) {
        this.id = id;
        this.player = player;
        this.match = match;
        this.cardType = cardType;
    }

    public Card() {
    }
    
    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }
    

  public enum CardType {
    YELLOW,
    RED,
    // Otros tipos si es necesario
    }
}

