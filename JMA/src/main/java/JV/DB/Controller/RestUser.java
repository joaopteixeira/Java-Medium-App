package JV.DB.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import JV.DB.Functions.FUser;
import JV.DB.Service.FileStorageService;
import JV.DB.Service.PostRepository;
import JV.DB.Service.UserRepository;
import JV.DB.UploadFile.UploadFileResponse;
import JV.DB.model.Country;
import JV.DB.model.District;
import JV.DB.model.Media;
import JV.DB.model.Post;
import JV.DB.model.User;
import JV.DB.model.Watch;

@RestController
@RequestMapping("muser")
public class RestUser {

	
	@Autowired
	UserRepository urep;
	
	
	@Autowired
	PostRepository prep;
	
	@Autowired
	FileStorageService fileStorageService;
	
	@Autowired
	FUser fuser;
	
	
	
	
	@GetMapping("/registry")
	public ResponseEntity<String> registry(@ModelAttribute User user,@RequestParam(name="password",defaultValue="") String password){
		
		
		String status = fuser.Registry(user, password);
	
		
		
		if(status.compareTo("Registado")==0) {
			
			return new ResponseEntity<String>("Registado",HttpStatus.ACCEPTED);
			
		}
		
		return new ResponseEntity<String>("nRegistado",HttpStatus.ACCEPTED);
		
		
	}
	
	

	@GetMapping("/editprofile")
	public ResponseEntity<?> editprofile(@RequestParam("firstname") String firstname ,
			@RequestParam("lastname") String lastname,@RequestParam("website") String website,@RequestParam("category") String category,@RequestParam("subcategory") String subcategory,@RequestParam("description") String description,@RequestParam("district") String district,@RequestParam("phonenumber") String phonenumber,@RequestParam("country") String country,@RequestParam("hash") String hash ) {
	

	Optional<User> u = urep.findByHashes(hash);
	
	if(u.isPresent()) {

	
	u.get().setFirstname(firstname);
	u.get().setLastname(lastname);
	u.get().setDistrict(district);
	u.get().setCountry(country);
	u.get().setPhonenumber(phonenumber);
	u.get().setWebsite(website);
	u.get().setDescription(description);
	u.get().setCategory(category);
	u.get().setSubcategory(subcategory);
	urep.save(u.get());
	
	
	Optional<User> use = urep.findById(u.get().getId());
	
	use.get().getHashes().clear();
	System.out.println(use.get().getId());
	
	return new ResponseEntity<>(use.get(),HttpStatus.OK);
	}

	return new ResponseEntity<>("null",HttpStatus.OK);
	
	
}
	
	
	
	
	@GetMapping("/1")
	public void encryptpass(@RequestParam("pass") String pass) {
		
		fuser.encryptpass(pass);
	}
	
	

	@GetMapping("addwatching")
	public ResponseEntity<?> addWatching(@RequestParam("hash") String hash,@RequestParam("iduser") String iduser){
		
		return new ResponseEntity<>(fuser.addWatching(hash, iduser),HttpStatus.OK);
		
		
	}
	

	
	@GetMapping("pesquser")
	public ResponseEntity<?> pesq(@RequestParam("hash") String hash,@RequestParam("name") String name,@RequestParam(value="district",defaultValue="Qualquer") String district,@RequestParam(value="category",defaultValue="Qualquer"
	) String category,@RequestParam(value="subcategory",defaultValue="Qualquer") String subcategory){
		
		if(district.compareTo("")==0) {
			district="Qualquer";
		}
		if(category.compareTo("")==0) {
			category="Qualquer";
		}
		if(subcategory.compareTo("")==0) {
			subcategory="Qualquer";
		}
		
		
		Optional<User> user = urep.findByHashes(hash);
		if(user.isPresent()) {
			List<User> users = fuser.getUserAdvacend(name, district, category, subcategory);
			
			if(users!=null) {
				return new ResponseEntity<List<User>>(users,HttpStatus.OK);
			}
			
			
			
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
		
		
		
	}

	@GetMapping("/changepass")
	public ResponseEntity<String> changePass(@RequestParam("hash") String hash,@RequestParam("holder") String holder,@RequestParam("nova") String nova) {
		
		return new ResponseEntity<String>(fuser.changePassword(hash, holder, nova),HttpStatus.OK);
	}
	
	
	@GetMapping("/getuser")
	public ResponseEntity<?> getuser(@RequestParam(name="hash",defaultValue="") String hash){
		
		
		Optional<User> user = urep.findByHashes(hash);
		
		if(user.isPresent()) {
			
			for(Watch w:user.get().getWatching()) {
				Optional<User> u1 = urep.findById(w.getIduser());
				w.setUsername(u1.get().getFirstname()+" "+u1.get().getLastname());
				w.setImguser(u1.get().getPathimage());
				
				user.get().getWatching().set(user.get().getWatching().indexOf(w), w);
			}
			for(Watch w:user.get().getWatched()) {
				Optional<User> u1 = urep.findById(w.getIduser());
				w.setUsername(u1.get().getFirstname()+" "+u1.get().getLastname());
				w.setImguser(u1.get().getPathimage());
				
				user.get().getWatched().set(user.get().getWatched().indexOf(w), w);
			}
			user.get().getHashes().clear();
			return new ResponseEntity<User>(user.get(),HttpStatus.OK);
		}
		
		
		return new ResponseEntity<String>("null",HttpStatus.OK);
		
		
		
	}
	
	
	@GetMapping("/resetpass")
	public ResponseEntity<String> resetPass(@RequestParam("email") String email){
		
		String status = fuser.sendEmailReset(email);
		
		if(status.compareTo("enviado pedido")==0) {
			return new ResponseEntity<String>("certo",HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("null",HttpStatus.OK);
		
	}
	
	@GetMapping("/getuserbyid")
	public ResponseEntity<?> getuserbyid(@RequestParam(name="hash",defaultValue="") String hash,@RequestParam("iduser") String iduser){
		
		
		Optional<User> user = urep.findByHashes(hash);
		Optional<User> u = urep.findById(iduser);
		
		if(user.isPresent() && u.isPresent()) {
			
			for(Watch w:u.get().getWatching()) {
				Optional<User> u1 = urep.findById(w.getIduser());
				w.setUsername(u1.get().getFirstname()+" "+u1.get().getLastname());
				w.setImguser(u1.get().getPathimage());
				
				u.get().getWatching().set(u.get().getWatching().indexOf(w), w);
			}
			for(Watch w:u.get().getWatched()) {
				Optional<User> u1 = urep.findById(w.getIduser());
				w.setUsername(u1.get().getFirstname()+" "+u1.get().getLastname());
				w.setImguser(u1.get().getPathimage());
				
				u.get().getWatched().set(u.get().getWatched().indexOf(w), w);
			}
			
			u.get().getHashes().clear();
			
			
			return new ResponseEntity<User>(u.get(),HttpStatus.OK);
		}
		
		
		return new ResponseEntity<String>("null",HttpStatus.OK);
		
		
		
	}
	
	@GetMapping("/getcountry")
	public ResponseEntity<List<Country>> getcountry(){
		
		
		return new ResponseEntity<List<Country>>(fuser.getCountry(),HttpStatus.OK);
	}
	
	@PostMapping("/upload")
	public UploadFileResponse uploadPost(@RequestParam("file") MultipartFile file,@RequestParam("hash") String hash){
		
		System.out.println(hash);
		
		Optional<User> user = urep.findByHashes(hash);
		
		if(user.isPresent()) {
			String fileName = fileStorageService.storeFile(file,FilenameUtils.getExtension(file.getOriginalFilename()));
	    	

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("upload/downloadFile/")
	                .path(fileName)
	                .toUriString();     
	        System.out.println(fileDownloadUri);
	        
	        user.get().setPathimage(fileDownloadUri);
	        
	        urep.save(user.get());
	        
	        
	        return new UploadFileResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize());
			
		}
		
		
		return null;

	}
	
	/*
	
	@RequestMapping("/mockupdata")
	public void mockupdata() {
	
		User u = new User();
        u.setFirstname("João");
        u.setLastname("Vilares");
        u.setPathimage("https://media.licdn.com/dms/image/C4D03AQHZzUy4bh7AuQ/profile-displayphoto-shrink_800_800/0?e=1547683200&v=beta&t=jiwpA-lq0RmmwHKM7gaQVeIoqKIqU1DQHQH5opCzzdI");
       prep
        
        Media m = new Media(Media.IMAGE,"https://g.foolcdn.com/image/?url=https%3A%2F%2Fg.foolcdn.com%2Feditorial%2Fimages%2F502052%2Fbitcoin4.jpg&w=700&op=resize");

        User u1 = new User();
        u1.setFirstname("Sergio");
        u1.setLastname("Figueiredo");
        u1.setPathimage("https://media.licdn.com/dms/image/C5603AQGmHWvUiLqI-A/profile-displayphoto-shrink_800_800/0?e=1547683200&v=beta&t=v6-mJlyXfZz81dbDBQjA0oOvkyWiuvVXPU26hq_3OXo");
        
        Media m1 = new Media(Media.IMAGE,"https://improvephotography.com/wp-content/uploads/2018/07/Sunrise-in-Rhodes-by-Rick-McEvoy-Photography-001.jpg");


        User u2 = new User();
        u2.setFirstname("José");
        u2.setLastname("Nascimento");
        u2.setPathimage("https://media.licdn.com/dms/image/C4D03AQGYeUV6j-OWEw/profile-displayphoto-shrink_800_800/0?e=1547683200&v=beta&t=rLzTOp1NNBeQXhpn4RXAob6vfzSW4cnI9OZ31R3aOUs");
        
        Media m2 = new Media(Media.IMAGE,"https://boygeniusreport.files.wordpress.com/2017/05/water.jpg?quality=98&strip=all&w=782");
		
        urep.save(u);
        urep.save(u1);
        urep.save(u2);
        
        
        
	} */
	
	
  @GetMapping("/comparObser")
	public ResponseEntity<String> getObserv(@RequestParam("iduser") String iduser, @RequestParam("idsession") String idsession){
		String check = "false";
		for(User u:urep.findAll()){
			if(u.getId().compareTo(iduser)==0) {
				for(Watch str:u.getWatched()) {
						System.out.println(str.getIduser());
						if(str.getIduser().compareTo(idsession)==0) {
							check = "true";
						}
					}
				}
			}
		System.out.println(check);
		
			return new ResponseEntity<String>(""+check,HttpStatus.OK);
		}
  
  
  @GetMapping("/sizeObser")
	public ResponseEntity<String> getsizeObserv(@RequestParam("iduser") String iduser){
		int size = 0;
		for(User u:urep.findAll()){
			if(u.getId().compareTo(iduser)==0) {
					size = u.getWatched().size();
				}
			}		
			return new ResponseEntity<String>(""+size,HttpStatus.OK);
		}
  
  @GetMapping("/getwatchedsById")
	public ResponseEntity<?> getWatcheds (@RequestParam("id_user") String id_user) {
		ArrayList<User> users = new ArrayList<>();
		for(User u : urep.findAll()) {
			if(u.getId().compareTo(id_user)==0) {
				for(Watch w:u.getWatched()) {
					for(User us:urep.findAll()) {
						if(w.getIduser().compareTo(us.getId())==0) {
							users.add(us);
						}
					}
				}
			}
		}
			
			
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
  
  
}
