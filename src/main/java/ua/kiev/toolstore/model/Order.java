package ua.kiev.toolstore.model;

import ua.kiev.toolstore.model.enums.OrderStatus;
import ua.kiev.toolstore.model.security.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @ManyToOne(optional = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Set<LineItem> lineItems = new HashSet<LineItem>();


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


    public BigDecimal getTotal() {

        BigDecimal total = BigDecimal.ZERO;

        for (LineItem item : lineItems) {
            total = total.add(item.getTotal());
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

    public Set<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Set<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Address getAddress() {
        if(address == null){
            return user.getAddress();
        }
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
