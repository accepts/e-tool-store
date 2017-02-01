//package ua.kiev.toolstore.model;
//
//import javax.persistence.Entity;
//import javax.persistence.OneToOne;
//
//@Entity
//public class ShippingAddress extends AbstractEntity {
//
//
//    private String country, state, city, street, apartment, zipCode;
//
////    @OneToOne(cascade = CascadeType.ALL)
////    @JoinColumn(name = "customer_id")
//
//    @OneToOne(mappedBy = "shippingAddress")
//    private Customer customer;
//
//
//
//    // ------------------------ Getter + Setter------------------------
//
//    public ShippingAddress() {
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public String getApartment() {
//        return apartment;
//    }
//
//    public void setApartment(String apartment) {
//        this.apartment = apartment;
//    }
//
//    public String getZipCode() {
//        return zipCode;
//    }
//
//    public void setZipCode(String zipCode) {
//        this.zipCode = zipCode;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//}
