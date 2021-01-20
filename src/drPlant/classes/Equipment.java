package drPlant.classes;

import DrPlant.enumerations.Use;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * * This entity class encapsulates the data of each equipment.
 * <ul>
 * <li><strong>equipment_id:</strong> The id of the equipment.</li>
 * <li><strong>equipment_name:</strong> The name of the equipment</li>
 * <li><strong>equipment_description:</strong> The description of the
 * equipment</li>
 * <li><strong>use:</strong> The use you can give to the equipment</li>
 * <li><strong>price:</strong>The equipment price</li>
 * <li><strong>image:</strong>An image of the equipment</li>
 * </ul>
 *
 * @author Eneko
 */

@XmlRootElement(name="equipment")
public class Equipment implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER =
            Logger.getLogger("drPlant.classes.Equipment");
    
    private Long id_equipment;   
    private String equipment_name;  
    private String equipment_description;  
    private float price; 
    private byte[] image;
    private Use uses; 
    private Shop shop;
    private Set<User> usuarios;
    
    public Equipment(){}
    
    /**
     *
     * @return ID the ID of the equipment
     */
    public Long getId_equipment() {
        return id_equipment;
    }

    /**
     * Setter of the equipment ID
     * @param id_equipment 
     */
    public void setId_equipment(Long id_equipment) {
        this.id_equipment = id_equipment;
    }

    /**
     *
     * @return A list of Users
     */
    public Set<User> getUsuarios() {
        return usuarios;
    }

    /**
     *
     * @param usuarios to set the User list
     */
    public void setUsuarios(Set<User> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     *
     * @return Shop the shop where it is on sale
     */
    public Shop getShop() {
        return shop;
    }

    /**
     *
     * @return String name of the equipment
     */
    public String getEquipment_name() {
        return equipment_name;
    }

    /**
     *
     * @param equipment_name to set the equipment name
     */
    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    /**
     *
     * @return String equipment description
     */
    public String getEquipment_description() {
        return equipment_description;
    }

    /**
     *
     * @param equipment_description to set the equipment description
     */
    public void setEquipment_description(String equipment_description) {
        this.equipment_description = equipment_description;
    }

    /**
     *
     * @return Float equipment price
     */
    public float getPrice() {
        return price;
    }

    /**
     *
     * @param price to set the equipment price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     *
     * @return The equipment Image
     */
    public byte[] getImagen() {

        return image;
    }

    /**
     *
     * @param imagen to set the equipment image
     */
    public void setImagen(byte[] imagen) {

        this.image = imagen;

    }

    /**
     *
     * @return Use the use of the equipment
     */
    public Use getUse() {
        return uses;
    }

    /**
     *
     * @param uses to set the equipment use
     */
    public void setUse(Use uses) {
        this.uses = uses;

    }
}
