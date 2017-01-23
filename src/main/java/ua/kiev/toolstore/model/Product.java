package ua.kiev.toolstore.model;

import org.springframework.util.Assert;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.model.enums.ProductCondition;
import ua.kiev.toolstore.model.enums.ProductStatus;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Product extends AbstractEntity{

    @Column(nullable = false)
    private String name;

    private String description, manufacturer;

    private ProductCategory category;
    private ProductCondition condition;
    private ProductStatus status;

    @Column(nullable = false)
    private BigDecimal price;

    private int unitInStock;

    @ElementCollection
    private Map<String, String> attributes = new HashMap<String, String>();


//    @Transient
//    private MultipartFile productImage;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonIgnore
//    private List<CartItem> cartItemList;



    //-------------------- Constructors  ------------------------------------------

    public Product() {
    }

    public Product(String name, String description, String manufacturer, ProductCondition condition, ProductCategory category, ProductStatus status, BigDecimal price, int unitInStock) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.condition = condition;
        this.category = category;
        this.status = status;
        this.price = price;
        this.unitInStock = unitInStock;
    }

    //------------------- Addition method -----------------------------------------

    public void setAttribute(String name, String value) {
        Assert.hasText(name);

        if (value == null) {
            this.attributes.remove(value);
        } else {
            this.attributes.put(name, value);
        }
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }


    //------------------- Getters + Setter's ---------------------------------------


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public ProductCondition getCondition() {
        return condition;
    }

    public void setCondition(ProductCondition condition) {
        this.condition = condition;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(int unitInStock) {
        this.unitInStock = unitInStock;
    }


}
