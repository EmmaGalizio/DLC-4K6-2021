package emma.galzio.tpidlcmotorbusquedaficheros.api.service;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.IndexationEngine;
import emma.galzio.tpidlcmotorbusquedaficheros.storage.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class IndexationController {

    @Value("${tpi.dlc.documentsResourceDirectory}")
    private String fileLocationPathString;
    @Autowired
    private IndexationEngine indexationEngine;

    private Path fileStorageLocation;


    @PostConstruct
    public void init(){
        this.fileStorageLocation = Paths.get(fileLocationPathString);

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Path storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void indexFile(Path filePath){

        indexationEngine.indexFiles(filePath.toFile());

    }




}
