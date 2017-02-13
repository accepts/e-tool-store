//package ua.kiev.toolstore.model;
//
//import ua.kiev.toolstore.model.enums.Role;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "users")
//public class User extends AbstractEntity {
//
//    // ---------------------------  System user info fields --------------------
//
//    private String name, email, password;
//
//    @Column(name = "enabled", nullable = false)
//    private boolean enabled = true;
//
//    @Enumerated(EnumType.STRING)
//    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
//    @Column(name = "role")
//    @ElementCollection(fetch = FetchType.EAGER)
//    private Set<Role> roles;
//
//
//    // ------------------------  Customer fields  -------------------------
//    private String firstName, lastName, phone;
//
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @JoinColumn(name="address_id")
//    private Address address;
//    //https://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
//    //http://internetka.in.ua/hibernate-one-to-one/
//    //https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl
//    //https://spring.io/blog/2011/02/10/getting-started-with-spring-data-jpa/
//    //https://www.petrikainulainen.net/spring-data-jpa-tutorial/
//
//
//
//
//
////    Constructors, Getter + Setter
//
//    public User() {
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }
//}