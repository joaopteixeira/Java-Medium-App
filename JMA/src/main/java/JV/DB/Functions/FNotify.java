package JV.DB.Functions;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JV.DB.Service.NotifyRepository;
import JV.DB.Service.UserRepository;
import JV.DB.model.Notify;
import JV.DB.model.User;

@Service("fnotify")
public class FNotify {
	
	
	@Autowired
	NotifyRepository nrep;
	
	@Autowired
	UserRepository urep;
	
	
	
	public List<Notify> notify(String hash){
		
		Optional<User> user = urep.findByHashes(hash);
		
		
		if(user.isPresent()) {
			List<Notify> aux = nrep.findByUseridAndEstado(user.get().getId(),Notify.NVISTO);
			
			if(!aux.isEmpty()) {
				
				for(Notify n:aux) {
					n.setEstado(Notify.VISTO);
					Optional<User> u = urep.findById(n.getUserdo());
					
					
					aux.get(aux.indexOf(n)).setUsername(u.get().getFirstname()+" "+u.get().getLastname());
					aux.get(aux.indexOf(n)).setUserimage(u.get().getPathimage());
					
					nrep.save(n);
				}
				
				return aux;
			}
		}
		
		
		
		return null;
	}
	public List<Notify> notifyAll(String hash){
		
		Optional<User> user = urep.findByHashes(hash);
		
		
		if(user.isPresent()) {
			List<Notify> aux = nrep.findByUseridAndTypeNotAndUserdoNot(user.get().getId(),Notify.TMSG,user.get().getId());
			
			if(!aux.isEmpty()) {
				for(Notify n:aux) {
					n.setEstado(Notify.VISTO);
					Optional<User> u = urep.findById(n.getUserdo());
					
					aux.get(aux.indexOf(n)).setUsername(u.get().getFirstname()+" "+u.get().getLastname());
					aux.get(aux.indexOf(n)).setUserimage(u.get().getPathimage());
					
				}
				return aux;
			}
		}
		
		
		
		return null;
	}
	
	
	public void saveNotify(Notify n) {
		nrep.save(n);
	}
	

}

