package ua.kiev.toolstore.model;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity {

    //TODO Customer

    private String firstName, lastName, phone;









//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "customer_user",
//            joinColumns = @JoinColumn(name="customer_id"),
//            inverseJoinColumns = @JoinColumn(name="user_id"))


//    Working variant!
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id", nullable = true)


    //http://stackoverflow.com/questions/5478328/jpa-jointable-annotation
//    @OneToOne(mappedBy = "customer", fetch = FetchType.EAGER)
//    @JoinTable(name = "customer_user",
//            joinColumns = @JoinColumn(name="customer_id"),
//            inverseJoinColumns = @JoinColumn(name="user_id"))


    @OneToOne(cascade=CascadeType.ALL, mappedBy="customer")
    @JoinTable(name="users",joinColumns=@JoinColumn(name="customer_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    private User user;

/*

http://stackoverflow.com/questions/5478328/jpa-jointable-annotation
https://hellokoding.com/jpa-one-to-one-foreignkey-relationship-example-with-spring-boot-maven-and-mysql/
http://hibernate-samples.blogspot.com/2011/10/one-to-one-association-using-join-table.html
https://github.com/spring-projects/spring-data-jpa/blob/master/src/main/java/org/springframework/data/jpa/repository/JpaRepository.java
https://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/

!!!
https://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/


*/





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

//    public ShippingAddress getShippingAddress() {
//        return shippingAddress;
//    }
//
//    public void setShippingAddress(ShippingAddress shippingAddress) {
//        this.shippingAddress = shippingAddress;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
