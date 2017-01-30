package ua.kiev.toolstore.util;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ua.kiev.toolstore.services.ProductService;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileManager {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(FileManager.class);

    @Value("${path.to.photo.storage}")
    private String storageFolder;

    @Autowired
    private ProductService productService;


    public String saveFileToLocalStorage(MultipartFile uploadedFile){

        //TODO validate file

        String uniqueID = UUID.randomUUID().toString() + "__";
        File fileDest = new File(storageFolder + uniqueID + uploadedFile.getOriginalFilename());
        fileDest.mkdirs();

        try {
            uploadedFile.transferTo(fileDest);
            LOG.debug("<---FileManager, File Destination: " + fileDest);
            return fileDest.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }







    private static class CustomValidator {
        /*
        * Name + correct extension
        * http://stackoverflow.com/questions/18208359/how-to-check-if-the-file-is-an-image
        * https://www.mkyong.com/regular-expressions/how-to-validate-image-file-extension-with-regular-expression/
        * http://stackoverflow.com/questions/4169713/how-to-check-a-uploaded-file-whether-it-is-a-image-or-other-file
        *
        * Size
        * http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html
        *
        * */


        // TODO size validator
        //TODO validate name + extension
        //   Assert = nameValidator(file);
        //TODO validate file type

    }








    // ******************  Retrieve picture file from DB and HDD ******************************

    public String getContentType(Long id){
        File file = new File(storageFolder + productService.findPictureByProductId(id));
        String contentType = new MimetypesFileTypeMap().getContentType(file);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        LOG.debug("<==== getContentType  {} : " + "(File ID): " + id + "(File contentType): " + contentType);
        return contentType;
    }


    public long getFileLength(Long id){
        File file = new File(storageFolder + productService.findPictureByProductId(id));
        LOG.debug("<==== getFileLength  {} : " + "(File ID): " + id + "(File length): " + file.length());
        return file.length();
    }


    public InputStreamResource getFileBody(Long id) throws IOException {
        LOG.debug("<==== getFileBody {}" + id);
        File file = new File(storageFolder + productService.findPictureByProductId(id));
        return new InputStreamResource( FileUtils.openInputStream(file));
    }

    // *********************  Delete file from HDD storage   ***************************************


    public boolean deleteFile(Long id){
        File file = new File(storageFolder + productService.findPictureByProductId(id));
        LOG.debug("<====Deleting file from HDD {}:" + file);
        return file.delete();
    }

}
