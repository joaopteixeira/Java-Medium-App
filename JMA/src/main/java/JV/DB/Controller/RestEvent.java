package JV.DB.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JV.DB.Functions.FEvent;
import JV.DB.Functions.FFeed;
import JV.DB.model.Event;

@RestController
@RequestMapping("mevent")
public class RestEvent {
	
	@Autowired
	FEvent fevent;
	
	
	@GetMapping("/getbydistrict")
	public ResponseEntity<?> getByDistrict(@RequestParam("hash") String hash,@RequestParam("district") String district){
		
		
		List<Event> ev = fevent.getEventByDistrict(hash, district);
		
		if(!ev.isEmpty()) {
			return new ResponseEntity<>(ev,HttpStatus.OK);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
	}
	@GetMapping("/getbyid")
	public ResponseEntity<?> getByDistrict(@RequestParam("hash") String hash){
		
		
		List<Event> ev = fevent.getEventById(hash);
		
		if(!ev.isEmpty()) {
			return new ResponseEntity<>(ev,HttpStatus.OK);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
	}
	
	@GetMapping("/getbyidevent")
	public ResponseEntity<?> getByIdevent(@RequestParam("hash") String hash,@RequestParam("idevent") String idevent){
		
		
		Event ev = fevent.getEvent(hash, idevent);
		
		if(ev!=null) {
			return new ResponseEntity<>(ev,HttpStatus.OK);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
	}
	
	@PostMapping("/addevent")
	public ResponseEntity<?> addEvent(@RequestBody Event event){
		return new ResponseEntity<>(fevent.addEvent(event.getIduser(), event),HttpStatus.OK);
	}
	
	

}
