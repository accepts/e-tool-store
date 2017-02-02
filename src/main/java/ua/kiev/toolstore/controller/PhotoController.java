package ua.kiev.toolstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.kiev.toolstore.util.FileManager;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.io.IOException;

@Controller
public class PhotoController {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(PhotoController.class);

    @Autowired
    private FileManager fileManager;


    @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable Long id) throws IOException {
        LOG.info("<======GET PHOTO " + id);
        long fileLength = fileManager.getFileLength(id);
        LOG.info("<---GET PHOTO (fileLength): " + fileLength);
        String contentType = fileManager.getContentType(id);
        LOG.info("<---GET PHOTO (contentType): " + contentType);
        InputStreamResource resource = fileManager.getFileBody(id);
        LOG.info("<---Retrieve image from PostgreSQL and  HDD {}" + id);

        return ResponseEntity.ok()
                .contentLength(fileLength)
                .contentType(MediaType.valueOf(contentType))
                .body(resource);
    }

}