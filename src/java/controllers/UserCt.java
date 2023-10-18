package controllers;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;
import persistence.UserJpaController;
import persistence.exceptions.NonexistentEntityException;
public class UserCt {
    private final UserJpaController controller = new UserJpaController();
    public void add(User user) {
        controller.create(user);
    }
    public List<User> getUsers() {
        return controller.findUserEntities();
    }
    public User getUserById(int id) {
        return controller.findUser(id);
    }
    public void update(User user) {
        try {
            controller.edit(user);
        } catch (Exception ex) {
            Logger.getLogger(UserCt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete(int id) {
        try {
            controller.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UserCt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public User getUserByUsername(String username) {
        return controller.findUserByUsername(username);
    }
    public int getUsersAmount() {
        return controller.getUserCount();
    }
    public List<User> getSomeUsers() {
        return controller.findUserEntities(8, 0);
    }
}