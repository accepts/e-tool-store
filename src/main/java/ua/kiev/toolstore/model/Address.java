package ua.kiev.toolstore.model;

import ua.kiev.toolstore.model.security.User;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Address extends AbstractEntity {


    private String country, state, city, street, apartment, zipCode;

    @OneToOne(mappedBy = "address")
    private User user;



    // ------------------------ Getter + Setter------------------------

    public Address() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
