package ua.kiev.toolstore.util;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileManager {


    public static String saveFileToLocalStorage(MultipartFile uploadedFile){

        //TODO validate file

        //TODO read destination from properties
        // TODO Create unique name
        //   Assert = nameValidator(file);

        File fileDest = new File("c:\\02\\" + uploadedFile.getOriginalFilename());

        try {
            uploadedFile.transferTo(fileDest);
            String finalName = fileDest.getName();
            return finalName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }





    private static class CustomValidator {

        //TODO validate name + extension

        //TODO validate file type

    }

}
