package drPlant.classes;

import drPlant.enumerations.UserPrivilege;
import drPlant.enumerations.Userstatus;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author saray
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER =
            Logger.getLogger("drPlant.classes.User");

    private Integer id;
    private String login;
    private String email;
    private String fullname;
    private Userstatus status;
    private UserPrivilege privilege;
    private String passwd;
    private java.sql.Date lastAccess;
    private java.sql.Date lastPasswdChange;
    private Set<UserPlant> plants;
    //private Set<Plant> plants;
    private Set<Equipment> equipments;

    /* public Set<Plant> getPlants() {
    return plants;
    }
    
    public void setPlants(Set<Plant> plants) {
    this.plants = plants;
    }*/
    
    
    /**
     *
     * @return the privilege of the user
     */
    public String getPrivilage() {
        return privilege.name();
    }

    /**
     * This method set the privilege of the user
     *
     * @param privilage
     */
    public void setPrivilage(UserPrivilege privilage) {
        this.privilege = privilage;
    }

    /**
     *
     * @return the status of the user
     */
    public String getStatus() {
        return status.name();
    }

    /**
     * Set the status of the user
     *
     * @param status
     */
    public void setStatus(Userstatus status) {
       this.status=status;
    }

    /**
     *
     * @return the Id of the user
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the id of the user
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return the login name of the user
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set the login name of the user
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the user
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return get the fullname of the user
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set the fullname of the user
     *
     * @param fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     *
     * @return the password
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Set the password
     *
     * @param passwd
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     *
     * @return last access date of the user
     */
    public Date getLastAccess() {
        return lastAccess;
    }

    /**
     * Set the last access date
     *
     * @param lastAccess
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = (java.sql.Date) lastAccess;
    }

    /**
     *
     * @return last password change date
     */
    public Date getLastPasswdChange() {
        return lastPasswdChange;
    }

    /**
     * Set last password change date
     *
     * @param lastPasswdChange
     */
    public void setLastPasswdChange(Date lastPasswdChange) {
        this.lastPasswdChange = (java.sql.Date) lastPasswdChange;
    }

    /**
     * Get list of plants asociated with the user
     * @return list of plants
     */
    public Set<UserPlant> getPlants() {
        return plants;
    }

    /**
     * Set all user´s plants 
     * @param plants 
     */
    public void setPlants(Set<UserPlant> plants) {
        this.plants = plants;
    }
    /**
    * Get list of equipments
    * @return list of user´s equipment
    */
    public Set<Equipment> getEquipments() {
        return equipments;
    }

    /**
     * Set user´s equipments
     * @param equipments 
     */
    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }

    /**
     * Get privilege
     * @return privilege
     */
    public UserPrivilege getPrivilege() {
        return privilege;
    }

    /**
     * Set user´s privilege
     * @param privilege 
     */
    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

}
