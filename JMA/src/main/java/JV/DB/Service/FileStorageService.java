package JV.DB.Service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import JV.DB.UploadFile.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service("FileStorageService")
public class FileStorageService {
	
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file,String ext) {
        // Normalize file name
    	String rand = UUID.randomUUID().toString();
    	
    	//String extensao = file.getOriginalFilename().toString(); //Apanhar o nome original e á frente meter lo no fim
    	//String ext = FilenameUtils.getExtension(file.getOriginalFilename());
    	//System.out.println("FICHEIRO NAME:   "+file.getOriginalFilename());
    	
        String fileName = StringUtils.cleanPath(rand.replace("-", "")) +"."+ ext;   //Para ter-mos a extensao do ficheiro (.png.jpec etc)

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    public String removeFile(String filename) {
    	Path targetLocation = this.fileStorageLocation.resolve(filename);
    	
    	try {
			Files.delete(targetLocation);
			return "aceite";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "naceite";
		}
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
