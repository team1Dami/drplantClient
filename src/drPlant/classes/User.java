package drPlant.classes;

import drPlant.enumerations.UserPrivilege;
import drPlant.enumerations.Userstatus;
import java.io.Serializable;
import java.util.Set;
import java.util.Date;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class encapsultaes the data of each user:
 * <ul>
 *  <li><strong>id:</strong> It's the id of the user. It's the identifier</li>
 *  <li><strong>login:</strong> It's the user's login</li>
 *  <li><strong>email:</strong> It's the user's email</li>
 *  <li><strong>fullname:</strong> It's the user's fullname</li>
 *  <li><strong>status:</strong> It's the user's status, that can be:
 *      <ul>
 *          <li>ENABLE</li>
 *          <li>DISABLE</li>
 *      </ul>
 *  </li>
 *  <li><strong>privilege:</strong> It's the user's privilege, that can be:
 *      <ul>
 *          <li>USER</li>
 *          <li>ADMIN</li>
 *      </ul>
 *  </li>
 *  <li><strong>passwd:</strong> It's the user's password, that it's going to be ciphered</li>
 *  <li><strong>lastAccess:</strong> It's the user's lastAccess</li>
 * </ul>
 * 
 * @author saray
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
   

    private Integer id;
    private String login;
    private String email;
    private String fullname;
    private Userstatus status;
    private UserPrivilege privilege;
    private String passwd;
    private Date lastAccess;
    private Date lastPasswdChange;
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
    public UserPrivilege getPrivilege() {
        return privilege;
    }

    /**
     * This method set the privilege of the user
     *
     * @param privilage
     */
    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    /**
     *
     * @return the status of the user
     */
    public Userstatus getStatus() {
        return status;
    }

    /**
     * Set the status of the user
     *
     * @param status
     */
    public void setStatus(Userstatus status) {
       this.status = status;
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
        this.lastAccess =  lastAccess;
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
        this.lastPasswdChange = lastPasswdChange;
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

}
