package controllers;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Player;
import persistence.PlayerJpaController;
import persistence.exceptions.NonexistentEntityException;
public class PlayerCt {
    private final PlayerJpaController controller = new PlayerJpaController();
    public void add(Player player) {
        controller.create(player);
    }
    public List<Player> getPlayers() {
        return controller.findPlayerEntities();
    }
    public Player getPlayerById(int id) {
        return controller.findPlayer(id);
    }
    public void update(Player player) {
        try {
            controller.edit(player);
        } catch (Exception ex) {
            Logger.getLogger(PlayerCt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete(int id) {
        try {
            controller.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PlayerCt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getPlayersAmount() {
        return controller.getPlayerCount();
    }
    public List<Player> getSomePlayers() {
        return controller.findPlayerEntities(8, 0);
    }
    public List<Player> getAllPlayers() {
        return controller.getPlayers();
    }
}

