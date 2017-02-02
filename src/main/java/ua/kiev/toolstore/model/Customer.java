//package ua.kiev.toolstore.model;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "customers")
//public class Customer extends AbstractEntity {
//
//    //TODO Customer
//
//    private String firstName, lastName, phone;
//
//
//
//
//
//
//
//
//
////    Working variant!
////    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
////    @JoinColumn(name = "user_id", nullable = true)
//
//
//    //http://stackoverflow.com/questions/5478328/jpa-jointable-annotation
////    @OneToOne(mappedBy = "customer", fetch = FetchType.EAGER)
////    @JoinTable(name = "customer_user",
////            joinColumns = @JoinColumn(name="customer_id"),
////            inverseJoinColumns = @JoinColumn(name="user_id"))
//
//
//    @OneToOne(cascade=CascadeType.ALL, mappedBy="customer")
//    @JoinTable(name="users",joinColumns=@JoinColumn(name="customer_id"),
//            inverseJoinColumns=@JoinColumn(name="user_id"))
//    private User user;
//
///*
//
//http://stackoverflow.com/questions/5478328/jpa-jointable-annotation
//https://hellokoding.com/jpa-one-to-one-foreignkey-relationship-example-with-spring-boot-maven-and-mysql/
//http://hibernate-samples.blogspot.com/2011/10/one-to-one-association-using-join-table.html
//https://github.com/spring-projects/spring-data-jpa/blob/master/src/main/java/org/springframework/data/jpa/repository/JpaRepository.java
//
//
//!!!
//https://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
//
//
//*/
//
//
//
//
//}
