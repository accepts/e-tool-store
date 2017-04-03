package ua.kiev.toolstore.util.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ua.kiev.toolstore.model.Product;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Component
public class ProductValidator {

    @Value("${max.picture.file.size.in.megabytes}")
    private long maxPictureFileSize;

    private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    protected static final LoggerWrapper LOG = LoggerWrapper.get(ProductValidator.class);


    public boolean productFieldsValidator(Product product) {
        if (product.getName().trim().isEmpty()) return false;
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) return false;
        return true;
    }


    // ******************  File validators (size + extension) ********************************

    //-------------- Validate file extension (jpg, gif, png... etc.)
    public boolean photoNameValidate(MultipartFile uploadedFile) {
        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        ;
        String name = uploadedFile.getOriginalFilename();
        return pattern.matcher(name).matches();
    }


    //-------------- Validate file size. Must be < 2 Mb
    public boolean photoSizeValidate(MultipartFile uploadedFile) {
        return !(uploadedFile.getSize() > maxPictureFileSize * 1024 * 1024);
    }


}
