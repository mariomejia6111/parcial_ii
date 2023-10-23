package persistence;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Player;
import persistence.exceptions.NonexistentEntityException;
public class PlayerJpaController implements Serializable {
    public PlayerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public PlayerJpaController() {
        this.emf = Persistence.createEntityManagerFactory("parcial_iiPU");
    }
    private EntityManagerFactory emf = null;
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public void create(Player player) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void edit(Player player) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            player = em.merge(player);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = player.getId();
                if (findPlayer(id) == null) {
                    throw new NonexistentEntityException("The player with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Player player;
            try {
                player = em.getReference(Player.class, id);
                player.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The player with id " + id + " no longer exists.", enfe);
            }
            em.remove(player);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public List<Player> findPlayerEntities() {
        return findPlayerEntities(true, -1, -1);
    }
    public List<Player> findPlayerEntities(int maxResults, int firstResult) {
        return findPlayerEntities(false, maxResults, firstResult);
    }
    private List<Player> findPlayerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Player.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    public List<Player> getPlayers() {
        TypedQuery<Player> query = getEntityManager().createQuery("SELECT p FROM Player p JOIN p.team t", Player.class);
        List<Player> results = query.getResultList();
        return results;
    }
    public Player findPlayer(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Player.class, id);
        } finally {
            em.close();
        }
    }
    public int getPlayerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Player> rt = cq.from(Player.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}