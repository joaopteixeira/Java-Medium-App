package JV.DB.Controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import JV.DB.Functions.FCategory;
import JV.DB.Functions.FEvent;
import JV.DB.Functions.FUser;
import JV.DB.Service.UserRepository;
import JV.DB.model.Category;
import JV.DB.model.District;
import JV.DB.model.Event;
import JV.DB.model.User;

@Controller
@RequestMapping("/wevent")
public class WebEvent {

	@Autowired
	FEvent fevent;
	
	@Autowired
	FUser fuser;
	
	@Autowired
	UserRepository userrep;
	
	@Autowired
	FCategory fcategory;
	
	
	
	@PostMapping("/newevent")
	public String newEvent(@RequestParam("title") String title,
			@RequestParam("iduser") String iduser,
			@RequestParam("district") String district,
			@RequestParam("description") String description,
			@RequestParam("address") String address,
			@RequestParam("date") String date,
			@RequestParam("category") String category,
			@RequestParam("subcategory") String subcategory,
			@RequestParam("pathimage") String pathimage,
			@RequestParam("username") String username,
			Model page,@RequestParam("personid") String personid,@RequestParam("main") String main,@RequestParam("frag") String frag,HttpSession session) {
		
		
		page.addAttribute("personid","you");
		
		Category cat = new Category(category,"");
		cat.getSubCategory().add(new Category(subcategory,""));
		
		
		District dist = new District(district);
		
		Event ev = new Event(title, iduser, dist, description, address, cat, date, pathimage, username);
		
		fevent.addEvent(ev.getIduser(), ev);
		
		 return "redirect:/feed?main=homepage&personid=you&frag=doevents";
		
	}
	
	@GetMapping("/getEventbyDistrict")
	public String getEventbyDistrict(Model model,@RequestParam("hash") String hash, @RequestParam("district") String district) {
	
		List<Event> ev = fevent.getEventByDistrict(hash, district);
		
		model.addAttribute("Event", ev);
		
		return "redirect:/feed?main=homepage&frag=frageventlist";
	}
	
	
/*
	@RequestMapping(value="/applyEvent", method=RequestMethod.POST)
	public String getEventbyDistrict(@ModelAttribute("User") User u,@RequestParam("iduser") String iduser) {
		
		try {
			
			String artista = u.getFirstname()+" "+u.getLastname();
			
			Optional<User> us = userrep.findById(iduser);
			
			
			
			String receptor = us.get().getEmail();
			
			
			fuser.applyEventEmail(receptor,artista);	
			
		    }catch(Exception e) {			
		    		
		    	return "redirect:/feed?main=homepage&frag=frageventlist";
	     	}
		
		return "redirect:/feed?main=homepage&frag=frageventlist";
	}
	*/
	
}
