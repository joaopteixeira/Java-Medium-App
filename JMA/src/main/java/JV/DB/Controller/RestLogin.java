package JV.DB.Controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JV.DB.Functions.FUser;
import JV.DB.Service.UserRepository;
import JV.DB.model.User;

@RestController
@RequestMapping("mlogin")
public class RestLogin {

	
	@Autowired
	UserRepository userRep;
	
	@Autowired
	FUser fuser;
	
	@RequestMapping(value="/validate", method=RequestMethod.GET)
	public ResponseEntity<String> getUser(@RequestParam("email") String email,@RequestParam("password") String password){
		
		String hash = fuser.login(email, password);
		
		if(hash!=null) {
			return new ResponseEntity<String>(hash,HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("null",HttpStatus.ACCEPTED);
		
	}
	
	

	
	
	
	
}
