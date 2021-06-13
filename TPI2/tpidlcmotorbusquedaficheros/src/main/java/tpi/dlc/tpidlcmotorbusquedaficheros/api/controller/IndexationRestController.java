package tpi.dlc.tpidlcmotorbusquedaficheros.api.controller;

import tpi.dlc.tpidlcmotorbusquedaficheros.api.dto.UploadFileResponse;
import tpi.dlc.tpidlcmotorbusquedaficheros.api.service.IndexationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.nio.file.Path;

@RestController
@RequestMapping("api/indexation")
public class IndexationRestController {

    @Autowired
    private IndexationController indexationController;
    @Autowired
    private ServletContext servletContext;

    @PostMapping
    public UploadFileResponse indexNewFile(@RequestParam("file")MultipartFile file){

        Path newFilePath = indexationController.storeFile(file);
        indexationController.indexFile(newFilePath);

        String fileName = file.getOriginalFilename();
        String fileUrl = newFilePath.toString();
        String fileUri = servletContext.getContextPath() +"/documentos/"+fileName;

        UploadFileResponse uploadFileResponse =
                        new UploadFileResponse(fileName, fileUrl, fileUri, file.getContentType());

        return uploadFileResponse;

    }

    @GetMapping
    public UploadFileResponse pingEndpoint(){
        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        uploadFileResponse.setFileName("Check");
        return uploadFileResponse;
    }


}
