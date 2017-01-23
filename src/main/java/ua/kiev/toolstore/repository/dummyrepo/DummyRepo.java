package ua.kiev.toolstore.repository.dummyrepo;

import org.springframework.stereotype.Repository;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.model.enums.ProductCategory;
import ua.kiev.toolstore.model.enums.ProductCondition;
import ua.kiev.toolstore.model.enums.ProductStatus;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DummyRepo {

    private static final AtomicLong counter = new AtomicLong();

    protected static final LoggerWrapper LOG = LoggerWrapper.get(DummyRepo.class);

    private static List<Product> productList;

    static{
        productList= populateDummyProducts();
    }


    private static List<Product> populateDummyProducts() {
        System.out.println("<===[DAO-REPO]=============(populateDummyProducts)");

        List<Product> products = new ArrayList<Product>();
        Product product1 = new Product();
        product1.setId(counter.incrementAndGet());
        product1.setName("Hammer");
        product1.setDescription("hand tool for smashing");
        product1.setManufacturer("Stenley");
        product1.setPrice(new BigDecimal(19.99));
        product1.setUnitInStock(3);
        product1.setCategory(ProductCategory.HAND_TOOL);
        product1.setCondition(ProductCondition.NEW);
        product1.setStatus(ProductStatus.IN_STOCK);
        product1.setAttribute("Shaft","Maple tree");
        product1.setAttribute("Steel","CPM-90");


        Product product2 = new Product();
        product2.setId(counter.incrementAndGet());
        product2.setName("Drill");
        product2.setDescription("electric tool for drilling holes");
        product2.setManufacturer("DUSS");
        product2.setPrice(new BigDecimal(300.55));
        product2.setUnitInStock(1);
        product2.setCategory(ProductCategory.ELECTRIC);
        product2.setCondition(ProductCondition.NEW);
        product2.setStatus(ProductStatus.SEVERAL_LEFT);
        product2.setAttribute("Power","1200W");
        product2.setAttribute("Currency","220V");
        product2.setAttribute("Frequency","50Hz");

        Product product3 = new Product();
        product3.setId(counter.incrementAndGet());
        product3.setName("Hydraulic pack");
        product3.setDescription("set of lifting equipment");
        product3.setManufacturer("Enerpac");
        product3.setPrice(new BigDecimal(560.49));
        product3.setUnitInStock(0);
        product3.setCategory(ProductCategory.HYDRAULIC);
        product3.setCondition(ProductCondition.USED);
        product3.setStatus(ProductStatus.OUT_OF_STOCK);


        products.add(product1);
        products.add(product2);
        products.add(product3);
        return products;
    }


    public List<Product> findAllProducts() {
        System.out.println("<===[DAO-REPO]=============(findAllProducts)");
        return productList;
    }


    public Product findById(long id) {
        System.out.println("<===[DAO-REPO]=============(findById)");
        for(Product p : productList){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }


    public void saveProduct(Product product) {
        System.out.println("<===[DAO-REPO]=============(saveProduct)");
        product.setId(counter.incrementAndGet());
        LOG.info("<--[!!!!!!]-----Saving product with contetnt {}", product);
        productList.add(product);
    }


    public void updateProduct(Product product) {
        System.out.println("<===[DAO-REPO]=============(updateProduct)");
        int index = productList.indexOf(product);
        productList.set(index, product);
    }


    public void deleteProductById(long id) {
        System.out.println("<===[DAO-REPO]=============(deleteProductById)");

        for (Iterator<Product> iterator = productList.iterator(); iterator.hasNext(); ) {
            Product product = iterator.next();
            if (product.getId() == id) {
                iterator.remove();
            }
        }
    }


//    public boolean isMessageExist(Product product) {
//        System.out.println("<===[DAO-REPO-REST]=============(isMessageExist)");
//        return findByHeader(message.getHeader())!=null;
//    }

}
