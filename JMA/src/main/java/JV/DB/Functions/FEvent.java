package JV.DB.Functions;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JV.DB.Service.EventRepository;
import JV.DB.Service.NotifyRepository;
import JV.DB.Service.UserRepository;
import JV.DB.model.Event;
import JV.DB.model.Notify;
import JV.DB.model.User;

@Service("fevent")
public class FEvent {
	
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	UserRepository urep;
	
	@Autowired
	FNotify fnotify;
	
	
	public List<Event> getEventByDistrict(String hash,String district){
		
		Optional<User> user = urep.findByHashes(hash);
		
		if(user.isPresent()) {
			ArrayList<Event> aux = new ArrayList<>();
			for(Event e:eventRepository.findByDistrictName(district)) {
				Optional<User> u = urep.findById(e.getIduser());
				e.setPathimage(u.get().getPathimage());
				e.setUsername(u.get().getFirstname()+" "+u.get().getLastname());
				aux.add(e);
				
			}
			
			return aux;
			
			
		}
		
		
		return null;
		
		
	}
public List<Event> getEventById(String hash){
		
		Optional<User> user = urep.findByHashes(hash);
		
		if(user.isPresent()) {
			ArrayList<Event> aux = new ArrayList<>();
			for(Event e:eventRepository.findByIduser(user.get().getId())) {
				Optional<User> u = urep.findById(e.getIduser());
				e.setPathimage(u.get().getPathimage());
				e.setUsername(u.get().getFirstname()+" "+u.get().getLastname());
				aux.add(e);
				
			}
			
			return aux;
			
		}
		
		
		return null;
		
		
	}


		public Event getEvent(String hash,String idevent) {
			Optional<User> user = urep.findByHashes(hash);
			System.out.println(user.get().getFirstname());
			Optional<Event> event = eventRepository.findById(idevent);
			
			Event e = event.get();
			
			if(user.isPresent() && event.isPresent()) {
				
				Optional<User> u = urep.findById(e.getIduser());
				e.setPathimage(u.get().getPathimage());
				e.setUsername(u.get().getFirstname()+" "+u.get().getLastname());
				
				return e;
			}
			return null;
		}
	
	public String addEvent(String Id,Event e) {
		
		Optional<User> user = urep.findById(Id);
		
		if(user.isPresent()) {
			
			eventRepository.save(e);
			
			
			
for(User u : urep.findByDistrict(e.getDistrict().getName())) {
				
				if(u.getId().compareTo(user.get().getId())==0) {
					break;
				}
				
				Notify n = new Notify(Notify.TEVENT, -1,u.getId(), user.get().getId(), user.get().getFirstname()+" "+user.get().getLastname(), user.get().getPathimage(), Notify.NVISTO);
				n.setIdevent(e.getId());
				fnotify.saveNotify(n);
				
			}
			
			return "aceite";
		}
		
		
		return "naceite";
	}
	
	public String removeEvent(String hash,String idevent) {
		Optional<Event> ev = eventRepository.findById(idevent);
		
		if(!ev.isPresent()) {return "null";}
		
		eventRepository.delete(ev.get());
		
		return "aceite";
		
		
	}
	
	
	

}
