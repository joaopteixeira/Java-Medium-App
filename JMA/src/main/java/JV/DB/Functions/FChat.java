package JV.DB.Functions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JV.DB.Service.ChatRepository;
import JV.DB.Service.UserRepository;
import JV.DB.model.Chat;
import JV.DB.model.Mensagem;
import JV.DB.model.Notify;
import JV.DB.model.User;

@Service("fchat")
public class FChat {

	
	@Autowired
	ChatRepository chatRep;
	
	@Autowired
	UserRepository userRep;
	
	@Autowired
	FNotify fnotify;
	
	
	public List<Chat> getChatsById(String hash){
		
		Optional<User> user = userRep.findByHashes(hash);
		
		if(user.isPresent()) {
			ArrayList<Chat> aux = new ArrayList<>();
			
			for(Chat c:chatRep.findByUsersId(user.get().getId())) {
				
				for(User u:c.getUsers()) {
					Optional<User> us = userRep.findById(u.getId());
					u.setFirstname(us.get().getFirstname());
					u.setLastname(us.get().getLastname());
					u.setPathimage(us.get().getPathimage());
					
				}
				
				
				for(Mensagem m:c.getMensagens()) {
					
					Optional<User> us = userRep.findById(m.getUser().getId());
					
					User u1 = new User();
					u1.setId(us.get().getId());
					u1.setFirstname(us.get().getFirstname());
					u1.setLastname(us.get().getLastname());
					u1.setPathimage(us.get().getPathimage());
					
					c.getMensagens().get(c.getMensagens().indexOf(m)).setUser(u1);
				
			}
				aux.add(c);
			}
			
			
			return aux;
		}
		
		return null;
		
	}
	public Chat getChatById(String hash,String idchat){
		
		
		Optional<Chat> chat = chatRep.findById(idchat);
		Optional<User> user = userRep.findByHashes(hash);
		boolean check=false;
		
		if(chat.isPresent() && user.isPresent()) {
			
			for(User u:chat.get().getUsers()) {
				if(u.getId().compareTo(user.get().getId())==0) {
					check=true;
				}
			}
			
			if(check) {
				for(User u:chat.get().getUsers()) {
					Optional<User> us = userRep.findById(u.getId());
					
					User u1 = new User();
					u1.setId(us.get().getId());
					u1.setFirstname(us.get().getFirstname());
					u1.setLastname(us.get().getLastname());
					u1.setPathimage(us.get().getPathimage());
					
					chat.get().getUsers().set(chat.get().getUsers().indexOf(u), u1);
				}
				
				for(Mensagem m:chat.get().getMensagens()) {
					
						Optional<User> us = userRep.findById(m.getUser().getId());
						
						User u1 = new User();
						u1.setId(us.get().getId());
						u1.setFirstname(us.get().getFirstname());
						u1.setLastname(us.get().getLastname());
						u1.setPathimage(us.get().getPathimage());
						
						m.setEstado(Mensagem.VISTO);
						m.setUser(u1);
						
						chat.get().getMensagens().set(chat.get().getMensagens().indexOf(m), m);
						//chat.get().getMensagens().get(chat.get().getMensagens().indexOf(m)).setUser(u1);
					
				}
				
				
				
				return chat.get();
			}
			
			
		}
		
		return null;
		
	}
	
	public Chat sendMsg(String hash,String idchat,String msg) {
		
		Optional<User> user = userRep.findByHashes(hash);
		Optional<Chat> chat = chatRep.findById(idchat);
		
		if(user.isPresent() && chat.isPresent()) {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();        
			// Using DateFormat format method we can create a string 
			// representation of a date with the defined format.
			String reportDate = df.format(today);

			User u1 = new User();
			u1.setId(user.get().getId());
			u1.setFirstname(user.get().getFirstname());
			u1.setLastname(user.get().getLastname());
			u1.setPathimage(user.get().getPathimage());
			
			
			Mensagem mensagem = new Mensagem(UUID.randomUUID().toString().substring(0, 6), u1, msg, reportDate,Mensagem.NVISTO);
			
			chat.get().getMensagens().add(mensagem);
			
			
			chatRep.save(chat.get());
			
			String userfrom="";
			
			for(User u:chat.get().getUsers()) {
				if(u.getId().compareTo(user.get().getId())!=0) {
					userfrom = u.getId();
				}
			}
			
			
			
			
			Notify n = new Notify(Notify.TMSG, 0,userfrom, user.get().getId(), user.get().getFirstname()+" "+user.get().getLastname(), user.get().getPathimage(), Notify.NVISTO);
			n.setIdchat(idchat);
			n.setIdmsg(mensagem.getId());
			n.setMsg(msg);
			
			fnotify.saveNotify(n);
			
			
			return chat.get();
			
			
		}
		
		return null;
		
	}
	
	
	public Chat createChat(String hash,String userdest) {
		
		
		Optional<User> user = userRep.findByHashes(hash);
		Optional<User> user1 = userRep.findById(userdest);
		
		Chat chatuser = null;
		
		for(Chat c:chatRep.findByUsersId(user.get().getId())) {
			for(User u:c.getUsers()) {
				if(u.getId().compareTo(user1.get().getId())==0) {
					chatuser = c;
				}
			}
		}
		
		
		
		//Optional<Chat> chat123 = chatRep.findByUsersIn(users);
		
		if(user.isPresent() && chatuser==null) {
			
			
			
			Chat chat = new Chat();
			User u = new User();
			u.setId(user.get().getId());
			u.setFirstname(user.get().getFirstname());
			u.setLastname(user.get().getLastname());
			u.setPathimage(user.get().getPathimage());
			User u1 = new User();
			u1.setId(user1.get().getId());
			u1.setFirstname(user1.get().getFirstname());
			u1.setLastname(user1.get().getLastname());
			u1.setPathimage(user1.get().getPathimage());
			chat.getUsers().add(u);
			chat.getUsers().add(u1);
			
			chatRep.save(chat);
			
			return chat;
			
		}else if(chatuser!=null) {
			
			for(Mensagem m:chatuser.getMensagens()) {
				chatuser.getMensagens().get(chatuser.getMensagens().indexOf(m)).setEstado(Mensagem.VISTO);
				
				
					Optional<User> us = userRep.findById(m.getUser().getId());
					
					User u1 = new User();
					u1.setId(us.get().getId());
					u1.setFirstname(us.get().getFirstname());
					u1.setLastname(us.get().getLastname());
					u1.setPathimage(us.get().getPathimage());
					
					chatuser.getMensagens().get(chatuser.getMensagens().indexOf(m)).setUser(u1);
				
			
				
			}
			
			chatRep.save(chatuser);
			
		
			
			for(User u:chatuser.getUsers()) {
				Optional<User> us = userRep.findById(u.getId());
				
				User u1 = new User();
				u1.setId(us.get().getId());
				u1.setFirstname(us.get().getFirstname());
				u1.setLastname(us.get().getLastname());
				u1.setPathimage(us.get().getPathimage());
				
				chatuser.getUsers().set(chatuser.getUsers().indexOf(u), u1);
			}
			
		
			
			
			return chatuser;
		}
		return null;
		
	}
	
	
	
	
}
