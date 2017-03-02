package ua.kiev.toolstore.model;

import ua.kiev.toolstore.model.enums.OrderStatus;
import ua.kiev.toolstore.model.security.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @ManyToOne(optional = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<LineItem> lineItems = new ArrayList<LineItem>();


    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;


    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String comment;


    //=============== Constructors + Methods ========================

    public Order() {
    }


    public Order(User user){
        this.user = user;
        this.address = user.getAddress();
        this.orderStatus = OrderStatus.ACTIVE;
    }


    public void addItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }


    public BigDecimal getSumOfItems() {
        BigDecimal total = BigDecimal.ZERO;

        for (LineItem item : lineItems) {
            total = total.add(item.getTotal());
        }

        return total;
    }


    public int getItemsAmount(){
        int total = 0;

        for (LineItem item : lineItems) {
            total = total + item.getAmount();
        }

        return total;
    }

    // =============== Getter + Setter ==============================


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

//    public Address getAddress() {
//        if(address == null){
//            return user.getAddress();
//        }
//        return this.address;
//    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



}
