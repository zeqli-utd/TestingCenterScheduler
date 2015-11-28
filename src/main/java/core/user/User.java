package core.user;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * This class is for store account info.
 */
@Entity
public class User {

    @Id
    protected String netId;

    @Basic(optional = false)
    protected String password;

    @Basic(optional = false)
    protected String firstName;

    @Basic(optional = false)
    protected String lastName;

    @Basic(optional = false)
    protected String email;

    @Basic(optional = false)
    private Authorization authorization;

    public User(){};

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }
}
