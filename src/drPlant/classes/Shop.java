package drPlant.classes;

import java.io.Serializable;
import java.util.Set;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class encapsultaes the data of each shop:
 * 
 * <ul>
 *  <li><strong>id_shop:</strong> It's the id of the shop. It's the identifier</li>
 *  <li><strong>shop_name:</strong> It's the shop's name</li>
 *  <li><strong>url:</strong> It's the shop's url</li>
 *  <li><strong>location:</strong> It's the shop's location</li>
 *  <li><strong>commission:</strong> It's the commission that we receive of the shop</li>
 *  <li><strong>email:</strong> It's the shop's email</li>
 *  <li><strong>plants:</strong> Collection of objects of the Plant class that have to sold</li>
 *  <li><strong>equipments:</strong> Collection of objects of the Equipment class that have to sold</li>
 * </ul>
 * 
 * @author saray
 */
@XmlRootElement
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER =
            Logger.getLogger("drPlant.classes.Shop");

    private Long id_shop;
    private String shop_name;
    private String url;
    private String location;
    private float commission;
    private String email;
    private Set<Plant> plants;
    private Set<Equipment> equipments;

    
    /**
     * 
     * @return the id_shop(long)
     */
    public Long getId() {
        return id_shop;
    }
    /**
     * 
     * @param id set the id_shop
     */
    public void setId(Long id) {
        this.id_shop = id;
    }
    /**
     * 
     * @return the shop_name
     */
    public String getShop_name() {
        return shop_name;
    }
    /**
     * 
     * @param shop_name set shop_name
     */
    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
    /**
     * 
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * 
     * @param url set the url
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * 
     * @return the location
     */
    public String getLocation() {
        return location;
    }
    /**
     * 
     * @param location set the location
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * 
     * @return the commission
     */
    public float getCommission() {
        return commission;
    }
    /**
     * 
     * @param commission  set the commission
     */
    public void setCommission(float commission) {
        this.commission = commission;
    }
    /**
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * 
     * @param email set the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return the plants of the shop
     */
    public Set<Plant> getPlants() {
        return plants;
    }
    /**
     * 
     * @return the equipments of the shop
     */
    public Set<Equipment> getEquipments() {
        return equipments;
    }
}
