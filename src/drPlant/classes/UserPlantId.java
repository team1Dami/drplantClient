package drPlant.classes;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * This entity class encapsulates the ids of the plant and user.
 * <ul>
 * <li><strong>userId: </strong>The id of the user</li>
 * <li><strong>scienceName: </strong>The id of the plant</li>
 * </ul>
 *
 *
 * @author Ruben
 */
@XmlRootElement
public class UserPlantId implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger("drPlant.classes.UserPlantId");
    
    private Integer userId;
    private String scienceName;
    
    /**
     * The constructor
     */
    public UserPlantId() {
    }
    
    /**
     * Method to set the values of userId and scienceName
     * @param userId the userId to be set
     * @param scienceName the scienceName of the plant to be set
     */
    public UserPlantId(Integer userId, String scienceName) {
        this.userId = userId;
        this.scienceName = scienceName;
    }
    
    /**
     * Method to obtain the userId
     * @return userId the id of the user
     */
    public Integer getUserId() {
        return userId;
    }
    
    /**
     * Method to set the value of userId
     * @param userId the userId to be set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    /**
     * Method to get the scienceName
     * @return scienceName the scienceName of the plant
     */
    public String getScienceName() {
        return scienceName;
    }

    /**
     * Method to set the value of scienceName of the plant
     * @param scienceName the scienceName of the plant
     */
    public void setScienceName(String scienceName) {
        this.scienceName = scienceName;
    } 
}
