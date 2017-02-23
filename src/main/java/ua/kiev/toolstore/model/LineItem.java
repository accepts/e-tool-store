package ua.kiev.toolstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class LineItem extends AbstractEntity {

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;

    private int amount;


    //=================== Constructors + Methods ========================

    public LineItem() {

    }

    public LineItem(Product product) {
        this.product = product;
        this.amount = 1;
        this.price = product.getPrice();
//        this(product, 1);
    }

//    public LineItem(Product product, int amount) {
//        this.product = product;
//        this.amount = amount;
//        this.price = product.getPrice();
//    }

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(amount));
    }


    //=================== Getter+Setter================


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
