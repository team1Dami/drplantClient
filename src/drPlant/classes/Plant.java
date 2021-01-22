
package drPlant.classes;

import drPlant.enumerations.Climate;
import drPlant.enumerations.PetFriendly;
import drPlant.enumerations.PlantType;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Logger;

/**
 * This entity class encapsulates the data of each Plague.
 * <ul>
 * <li><strong>scienceName:</strong> The id of the plant. Name of the plant</li>
 * <li><strong>commonName:</strong> Also the name of the plant known by
 * users</li>
 * <li><strong>description:</strong> The description of the plant</li>
 * <li><strong>cares:</strong> The description how to care the plant</li>
 * <li><strong>climate:</strong> It's the clima of the plant, that can be:
 * <ul>
 * <li>dry</li>
 * <li>wet</li>
 * <li>hot</li>
 * <li>Cold<li </ul> </li>
 * <li><strong>type:</strong> Password of the user</li>
 * <ul>
 * <li>succulent</li>
 * <li>indoor</li>
 * <li>outdoor</li>
 * </ul>
 * <li><strong>petfriendly:</strong> Last time the user login in the app</li>
 * <ul>
 * <li>dog</li>
 * <li>cat</li>
 * <li>both</li>
 * <li>no</li>
 * </ul>
 * <li><strong>image:</strong> Last time the user change the password </li>
 * </ul>
 *
 * @author Ruben
 */

public class Plant implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER =
            Logger.getLogger("drPlant.classes.Plant");
    
    private String scienceName;
    private String commonName;
    private String description;
    private String cares;
    private float wateringFrequence;
    private PetFriendly petfriendly;
    private PlantType plantType;
    private Climate climate;
    private byte[] image;
    private Set<UserPlant> users;

    private Set<Shop> shops;

    private Set<Plague> plagues;

    /**
     *
     * @return list of plagues
     */
    public Set<Plague> getPlagues() {
        return plagues;
    }

    /**
     * Set list of plagues
     * @param plagues 
     */
    public void setPlagues(Set<Plague> plagues) {
        this.plagues = plagues;
    }

    /**
     *
     * @return science name the science name (id) of the plant
     */
    public String getScienceName() {
        return scienceName;
    }

    /**
     * Set the scient name 
     * @param scienceName
     */
    public void setScienceName(String scienceName) {
        this.scienceName = scienceName;
    }

    /**
     *
     * @return commonName of the plant
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     *
     * @param commonName set common name 
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     *
     * @return description of the plant
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return cares of the plant
     */
    public String getCares() {
        return cares;
    }

    /**
     * Set cares
     * @param cares
     */
    public void setCares(String cares) {
        this.cares = cares;
    }

    /**
     *
     * @return wateringFrequence time to water the plant
     */
    public float getWateringFrequence() {
        return wateringFrequence;
    }

    /**
     * Set the watering frequence
     * @param wateringFrequence
     */
    public void setWateringFrequence(float wateringFrequence) {
        this.wateringFrequence = wateringFrequence;
    }

    /**
     *
     * @return petFriendly the PetFrindly enum
     */
    public PetFriendly getPetfriendly() {
        return petfriendly;
    }

    /**
     * Set the petfriendly enum
     * @param petfriendly
     */

    public void setPetfriendly(PetFriendly petfriendly) {
        this.petfriendly = petfriendly;
    }

    /**
     *
     * @return plantType enum
     */
    public PlantType getPlantType() {
        return plantType;
    }

    /**
     * Set plantType enum
     * @param plantType
     */
    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    /**
     *
     * @return climate Climate enum
     */
    public Climate getClimate() {
        return climate;
    }

    /**
     * Set the climate enum
     * @param climate
     */
    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    /**
     *
     * @return image the byte array 
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Set the array of bytes which is the image
     * @param image
     */
    public void setImage(byte[] image) {
        this.image = image;

    }

    /**
     *
     * @return list of users
     */
    public Set<UserPlant> getUsers() {
        return users;
    }

    /**
     * Set list of users
     * @param users
     */
    public void setUsers(Set<UserPlant> users) {
        this.users = users;
    }

    /**
     *
     * @return list of shops
     */
    public Set<Shop> getShops() {
        return shops;
    }

    /**
     * Set list of shops
     * @param shops
     */
    public void setShops(Set<Shop> shops) {
        this.shops = shops;
    }

}
