package drPlant.classes;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.logging.Logger;

/**
 * This entity class encapsulates the data of each Plague.
 * <ul>
 * <li><strong>userId:</strong> The user id that is in the embeddable class
 * </li>
 * <li><strong>scienceName:</strong> The plant id that is in the embeddable
 * class</li>
 * <li><strong>dateWatering:</strong> The last wateringo of the plant</li>
 * </ul>
 *
 * @author Ruben
 */

public class UserPlant implements Serializable {
    
    private static final Logger LOGGER
            = Logger.getLogger("drPlant.classes.UserPlant");
    
    private UserPlantId id;
    private User user;
    private Plant plant;
    private Timestamp dateWatering;
    
    /**
     * 
     * @return id the id of the user_plant
     */
    public UserPlantId getId() {
        return id;
    }

    /**
     * 
     * @param id the id to be set
     */
    public void setId(UserPlantId id) {
        this.id = id;
    }
    
    /**
     * 
     * @return user the user that have this plants
     */
    public User getUser() {
        return user;
    }
    
    /**
     * 
     * @param user the user to be set
     */
    public void setUser(User user) {
        this.user = user;
    }
    /**
     * 
     * @return plant the plant of the user
     */
    public Plant getPlant() {
        return plant;
    }
    
    /**
     * 
     * @param plant the plant to be set
     */
    public void setPlant(Plant plant) {
        this.plant = plant;
    }
    
    /**
     * 
     * @return dateWatering the dateWatering of the plant
     */
    public Timestamp getDateWatering() {
        return dateWatering;
    }

    /**
     * 
     * @param dateWatering the dateWatering to be set
     */
    public void setDateWatering(Timestamp dateWatering) {
        this.dateWatering = dateWatering;
    } 
}
