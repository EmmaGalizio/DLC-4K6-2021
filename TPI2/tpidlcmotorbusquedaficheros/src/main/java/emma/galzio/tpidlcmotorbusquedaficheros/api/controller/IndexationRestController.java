package emma.galzio.tpidlcmotorbusquedaficheros.api.controller;

import emma.galzio.tpidlcmotorbusquedaficheros.api.dto.UploadFileResponse;
import emma.galzio.tpidlcmotorbusquedaficheros.api.service.IndexationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import java.nio.file.Path;

@RestController()
public class IndexationRestController {

    @Autowired
    private IndexationController indexationController;
    @Autowired
    private ServletContext servletContext;

    @PostMapping("api/indexation")
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

    @GetMapping("api/indexation")
    public UploadFileResponse pingEndpoint(){
        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        uploadFileResponse.setFileName("Check");
        return uploadFileResponse;
    }


}
