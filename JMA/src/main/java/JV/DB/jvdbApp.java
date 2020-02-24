
package JV.DB;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import JV.DB.UploadFile.FileStorageProperties;
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties({
	FileStorageProperties.class
})



public class jvdbApp {

	public static void main(String[] args) {
		SpringApplication.run(jvdbApp.class, args);	
		System.out.println("---------JV-DB-- on ");
		
		
		
		
		
	}
}
