package JV.DB.Controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JV.DB.Functions.FUser;
import JV.DB.Service.UserRepository;
import JV.DB.model.User;

@Controller
public class WebLogin {
	
	
	
	@Autowired
	UserRepository userRepo;
	
	
	@Autowired
	FUser fuser;


	
	@RequestMapping(value="/registry", method=RequestMethod.POST)
	public String UserRegistry(@ModelAttribute("User") User u,@RequestParam("password") String password, Model page,HttpSession session){

		
		System.out.println("");
		
		
		Optional<User> us = userRepo.findByEmail(u.getEmail());
		
		if(us.isPresent()) {
			
			
			return "redirect:/index.html"; 

			
			
		}
	
		u.setDescription("");
		u.setPhonenumber("");
		
		
		fuser.Registry(u,password);
		session.setAttribute("User",u);
		return UserLogin(u,page,password,session);
		
		    // 
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String UserLogin(@ModelAttribute("User") User u, Model page, @RequestParam("password") String password,HttpSession session){
		
		
	//	fuser.login(u.getEmail(), password);
		
		String hash = fuser.login(u.getEmail(), password);
		
		Optional<User> us = userRepo.findByHashes(hash);
		
		
		
		if(us.isPresent()) {
			
			session.setAttribute("hash", hash);
			//page.addAttribute("User",us.get());
			session.setAttribute("User", us.get());

			return "redirect:/feed?main=homepage&frag=post&personid=you";
			
		}
		
		return "redirect:/index";
	}
	
	
	
	
	
	@RequestMapping(value="/resetPassword", method=RequestMethod.POST)
	public String resetPassword(@RequestParam("usermail") String usermail){
		
		try {
			
			fuser.sendEmailReset(usermail);	
			
		    }catch(Exception e) {			
		    		
		    	return "redirect:/index";   //Caso não meta nenhum que exista!
	     	}
		
				
	return "redirect:/index";      // Deu, enviou volta para o home
	}
	

}
