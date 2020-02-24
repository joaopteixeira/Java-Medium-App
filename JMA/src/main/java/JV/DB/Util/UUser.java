package JV.DB.Util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import JV.DB.Functions.FUser;
import JV.DB.Service.UserRepository;
import JV.DB.model.User;

public class UUser {
	
	
	@Autowired
	UserRepository urep;
	
	
	public String getuser(String id) {
		
		
		Optional<User> user = urep.findById(id);
		
		
		String fullname = user.get().getFirstname()+user.get().getLastname();
		
		return fullname;
	}

}
