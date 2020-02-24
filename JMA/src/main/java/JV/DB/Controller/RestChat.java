package JV.DB.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JV.DB.Functions.FChat;
import JV.DB.Service.ChatRepository;
import JV.DB.model.Chat;
import JV.DB.model.Mensagem;

@RestController
@RequestMapping("mchat")
public class RestChat {


	@Autowired
	FChat fchat;
	
	@Autowired
	ChatRepository chatRep;
	
	@GetMapping("/get")
	public ResponseEntity<?> getChat(@RequestParam(name="hash",defaultValue="") String hash){

		List<Chat> chats = fchat.getChatsById(hash);


		if(chats!=null) {
		ArrayList<Chat> aux = new ArrayList<>();
		for(Chat c:chats) {
			if(c.getMensagens().size()>0) {
				aux.add(c);
			}
		}


			return new ResponseEntity<List<Chat>>(aux,HttpStatus.OK);
		}


		return new ResponseEntity<>("null",HttpStatus.OK);
	}


	@GetMapping("/getchatbyid")
	public ResponseEntity<?> getChatById(@RequestParam("idchat") String idchat,@RequestParam(name="hash",defaultValue="") String hash){

		Chat c =fchat.getChatById(hash,idchat);

		if(c!=null) {
			return new ResponseEntity<Chat>(c,HttpStatus.OK);
		}

		return new ResponseEntity<>("null",HttpStatus.OK);

	}


	@GetMapping("/createchat")
	public ResponseEntity<?> createChat(@RequestParam(name="hash",defaultValue="") String hash,@RequestParam("iduser") String iduser){

		Chat c = fchat.createChat(hash, iduser);

		if(c!=null) {
			return new ResponseEntity<Chat>(c,HttpStatus.ACCEPTED);
		}


		return new ResponseEntity<>("null",HttpStatus.ACCEPTED);




	}


	@RequestMapping(value="/sendmsg",method=RequestMethod.GET)
	public ResponseEntity<?> sendMsg(@RequestParam(name="hash",defaultValue="") String hash,@RequestParam("idchat") String idchat,@RequestParam("msg") String msg){

		Chat chat =fchat.sendMsg(hash, idchat, msg);
		if(chat!=null) {
			return new ResponseEntity<Chat>(chat,HttpStatus.OK);
		}else {
			return new ResponseEntity<>("null",HttpStatus.OK);
		}




	}


	@GetMapping("/getdate")
	public ResponseEntity<?> getDate(@RequestParam("data") String data){
		String diferenca;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		long diffInMillies;
		long diff;
		String reportDate = df.format(today);
		try {
			Date dataserver = df.parse(reportDate);
			Date datamessage = df.parse(data);
			diffInMillies = Math.abs(dataserver.getTime() - datamessage.getTime());

		    if(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) == 0) {
		    	if(TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS) == 0) {
			    	if(TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS) == 0) {
			    		if(TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS) == 0) {
					    	diferenca = "Agora Mesmo";
					    }
			    		else {
			    			diferenca = "A "+TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS)+" segundos";
			    		}
				    }else {
				    	diferenca = "A "+TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS)+" minutos";
				    }

			    }else {
			    	diferenca = "A "+TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS)+" horas";
			    }
		    }else{
		    	diferenca = "A "+TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)+" dias";
		    }


		    return new ResponseEntity<String>(String.valueOf(diferenca),HttpStatus.OK);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return new ResponseEntity<>("null",HttpStatus.OK);
	}

}
