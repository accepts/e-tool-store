package ua.kiev.toolstore.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileManager {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(FileManager.class);

    @Value("${path.to.photo.storage}")
    private String value;


    //TODO TOmacat problem with file open

    public String saveFileToLocalStorage(MultipartFile uploadedFile){

        //TODO validate file

        String uniqueID = UUID.randomUUID().toString() + "__";
        File fileDest = new File(value + uniqueID + uploadedFile.getOriginalFilename());
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

}
