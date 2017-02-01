package ua.kiev.toolstore.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity {

    //TODO Customer

    private String firstName, lastName, phone;


    // TODO correct cascading
    // _http://stackoverflow.com/questions/29600464/how-does-hibernate-work-with-onetoone-and-cascade-all-using-spring
    @OneToOne
    @JoinColumn(name = "shippingaddress_id")
    private ShippingAddress address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Customer() {
    }



    //-------------------------- Getter + Setter -------------------------

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ShippingAddress getAddress() {
        return address;
    }

    public void setAddress(ShippingAddress address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
