package JV.DB.Functions;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.HeaderLinksResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import JV.DB.Service.CountryRepository;
import JV.DB.Service.LoginRepository;
import JV.DB.Service.UserRepository;
import JV.DB.Util.WebServices;
import JV.DB.model.Country;
import JV.DB.model.Login;
import JV.DB.model.User;
import JV.DB.model.Watch;

import org.apache.commons.mail.*;
 
@Service("fuser")
public class FUser {                   //Funcoes pro USER  

	@Autowired
	UserRepository userRep;
	
	@Autowired
	LoginRepository loginRep;
	
	@Autowired
	CountryRepository countryrep;
	
	
	
	
	public FUser() {        
		super();
	}


	
	



	public String login(String email,String password) {
		
		String hash = UUID.randomUUID().toString();
		//Optional<Login> login = loginRep.findByEmailAndPassword(email, password);
		Optional<Login> login = loginRep.findByEmail(email);
		
		if(login.isPresent() && dsycryption(login.get().getPassword()).compareTo(password)==0) {
			Optional<User> u = userRep.findByEmail(login.get().getEmail());
			
			//u.get().setHashes();
			if(u.get().getHashes().size()>=5) {
				u.get().getHashes().set(0, hash);
			}else {
				u.get().getHashes().add(hash);
			}
			
			userRep.save(u.get());
			return hash;
		}
		
		
		
		return null;	
	}
	
	

    public BufferedImage resizeImage(BufferedImage originalImage, int type){
    	
    int heigth = originalImage.getHeight();
    int width = originalImage.getWidth();
    int maior = 0;
    int menor = 0;
    int qual = -1;
    int cont=0;;
    
    if(heigth>width) {
    	maior = heigth;
    	menor = width;
    	qual=0;
    }else {
    	qual=1;
    	maior = width;
    	menor = heigth;
    }
    
    if(maior>300) {
		do {
			maior=maior/2;
			cont++;
		}while(maior>300);
	}
    
    for(int i=0;i<cont;i++) {
    	menor=menor/2;
    }
    
    if(qual==0) {
    	heigth = maior;
    	width = menor;
    }else {
    	heigth = menor;
    	width = maior;
    }
    	
	BufferedImage resizedImage = new BufferedImage(width, heigth, originalImage.getType());
	Graphics2D g = resizedImage.createGraphics();
	g.setRenderingHint(RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);
	g.drawImage(originalImage, 0, 0, width, heigth, null);
	g.dispose();
	
			
		
	return resizedImage;
    }
	
	
	public BufferedImage cropImageSquare(byte[] image) throws IOException {
		  // Get a BufferedImage object from a byte array
		  InputStream in = new ByteArrayInputStream(image);
		  BufferedImage originalImage = ImageIO.read(in);
		  
		  // Get image dimensions
		  int height = originalImage.getHeight();
		  int width = originalImage.getWidth();
		  
		  // The image is already a square
		  
		  
		  // Compute the size of the square
		  int squareSize = (height > width ? width : height);
		  
		  // Coordinates of the image's middle
		  int xc = width / 2;
		  int yc = height / 2;
		  
		  // Crop
		  BufferedImage croppedImage = originalImage.getSubimage(
		      xc - (150 / 2), // x coordinate of the upper-left corner
		      yc - (150 / 2), // y coordinate of the upper-left corner
		      150,            // widht
		      150             // height
		  );
		  
		  return croppedImage;
		}

	public String Registry(User u,String password) {
		
		Optional<Login> userOp = loginRep.findByEmail(u.getEmail());
		
		
		
		if(userOp.isPresent()) {
			return "ja existe o email";
		}else {
			
			loginRep.save(new Login(u.getEmail(),encryptpass(password)));
			u.setTokkensquantity(10);	
			u.setPathimage(WebServices.SERVER+"/upload/downloadFile/userdefault.png");//Quantidade Inicial de Tokkens
			u.setAccactivated(0);
			u.setDescription("");
			
			userRep.save(u);		
			
		}
		
		
		return "Registado";
		
	}
	
	public String encryptpass(String pass) {
		
		int size = pass.length();
		
		
		String[] chars = new String[size];
		
		
		for(int i=0;i<size;i++) {
			chars[i] = String.valueOf(pass.charAt(i));
		}
		
			
		String p = "";
		for(int i=0;i<size;i++) {
			Random r = new Random();
			int num = r.nextInt(5);
			p+=chars[i]+String.valueOf(num)+UUID.randomUUID().toString().substring(0, num);
		}
		
		
		//System.out.println(p);
		
		return p;
		
		//dsycryption(p);
		
		
		
		
		
	}
	
	public String dsycryption(String pass) {
		
		int size = pass.length();
		
		
		String[] chars = new String[size];
		
		
		for(int i=0;i<size;i++) {
			chars[i] = String.valueOf(pass.charAt(i));
		}
		
		String[] passnew = new String[size];
		
		passnew[0]=chars[0];
		int i=0;
		for(int j=1;i<size-1;j++) {
			if(Integer.valueOf(chars[i+1])+i+2<size) {
				i=2+i+Integer.valueOf(chars[i+1]);
				
				passnew[j] = chars[i];
			}else {
				break;
			}
			
		}
		
		String p = "";
		for(int j=0;j<passnew.length;j++) {
			if(passnew[j]!=null)
			p+=passnew[j];
		}
		
		System.out.println("~*******");
		return p;
		
		
	}
	
	
	public String changePassword(String hash,String holder,String nova) {
		
		Optional<User> user = userRep.findByHashes(hash);
		
		if(user.isPresent()) {
			
			//Optional<Login> login = loginRep.findByEmailAndPassword(user.get().getEmail(), holder);
			
			Optional<Login> login = loginRep.findByEmail(user.get().getEmail());
			
			if(login.isPresent() && dsycryption(login.get().getPassword()).compareTo(holder)==0) {
				
				login.get().setPassword(encryptpass(nova));
				
				loginRep.save(login.get());
				return "aceite";
			}else {
				return "erroholder";
			}
			
		}else {
			return "null";
		}
		
		
	}
	
	
	public List<User> getUserAdvacend(String name,String district,String category,String subcategory){
		String firstname = "";
		String lastname = "";
		
		if(name.contains(" ")) {
			firstname = name.substring(0, name.indexOf(" "));
			lastname = name.substring(name.indexOf(" "),name.length()-1);
		}else {
			firstname = name;
			lastname = name;
		}
		
		System.out.println(category);
		System.out.println(district);
		if(category.compareTo("Qualquer")==0 && district.compareTo("Qualquer")==0) {
			return userRep.findByFirstnameContainingOrLastnameContaining(firstname,lastname);
		}
		else if(category.compareTo("Qualquer")==0 && district.compareTo("Qualquer")!=0) {
			return userRep.findByDistrict(district);
		}else if(category.compareTo("Qualquer")!=0 && subcategory.compareTo("Qualquer")!=0 && district.compareTo("Qualquer")!=0) {
			return userRep.findByDistrictAndCategoryAndSubcategory(district, category, subcategory);
		}else if(category.compareTo("Qualquer")!=0 && subcategory.compareTo("Qualquer")==0 && district.compareTo("Qualquer")!=0) {
			return userRep.findByDistrictAndCategory( district, category);
		}else if(category.compareTo("Qualquer")!=0 && subcategory.compareTo("Qualquer")!=0 && district.compareTo("Qualquer")==0) {
			return userRep.findByCategoryAndSubcategory( category, subcategory);
		}else if(category.compareTo("Qualquer")!=0 && subcategory.compareTo("Qualquer")==0 && district.compareTo("Qualquer")==0) {
			return userRep.findByCategory( category);
		}
		
		return null;
		
	}
	
	
	
	public List<User> getUserContainig(String name){
		
		String firstname = "";
		String lastname = "";
		
		if(name.contains(" ")) {
			firstname = name.substring(0, name.indexOf(" "));
			lastname = name.substring(name.indexOf(" "),name.length()-1);
		}else {
			firstname = name;
			lastname = name;
		}
		
		System.out.println(firstname);
		System.out.println(lastname);
		
		
		return userRep.findByFirstnameContainingOrLastnameContaining(firstname,lastname);
		
	}
	
	public String sendEmailReset(String usermail) {
		
	
		
		Optional<Login> userOp = loginRep.findByEmail(usermail);

	
		System.out.println("User id: "+userOp.get().getId() + " reseted pass ");
		
		
		
		if(userOp.isPresent()) {

			
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthentication("artlinkrecovery@gmail.com", "thalia2018");
			//email.setAuthentication("admin@artlink.pt","thaliapt18");
			//email.setHostName("webdomain02.dnscpanel.com");
			//email.setHostName("pop.sapo.pt ");
			//email.setSmtpPort(110);
			//email.setAuthentication("apoio.artlink@sapo.pt", "Thalia2018");
			email.setSSL(true);
		
			
			
		try {
				
			String newPassword = UUID.randomUUID().toString().substring(0,8).replace("-", "");	
			
			

			
				email.setFrom("artlinkrecovery@gmail.com");
				email.setSubject("Recuperação de Password da sua conta Artlink");
				email.setMsg("A sua nova password:  "
						+ newPassword + "   :Se não efectuou este pedido por favor contacte a administração da Artlink");
				email.addTo(usermail);
				email.send();
				
				
				
				userOp.get().setPassword(encryptpass(newPassword));
				loginRep.save(userOp.get());
				
						
				System.out.println("email enviado para: "+ usermail +"");
			
				
			
		
			}catch(EmailException e) {
				e.printStackTrace();
			 }
			

		
		
			
		}else {
			
			return "Conta nao existe o email";		
				}
		
	return "enviado pedido";
	}
	
	
	
public String sendEmailNovaPassword(String usermail,String newpass) {
		
	
		
		Optional<Login> userOp = loginRep.findByEmail(usermail);

	
		System.out.println("User id: "+userOp.get().getId() + " reseted pass ");
		
		
		
		if(userOp.isPresent()) {

			
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthentication("artlinkrecovery@gmail.com", "thalia2018");
			//email.setAuthentication("admin@artlink.pt","thaliapt18");
			//email.setHostName("webdomain02.dnscpanel.com");
			//email.setHostName("pop.sapo.pt ");
			//email.setSmtpPort(110);
			//email.setAuthentication("apoio.artlink@sapo.pt", "Thalia2018");
			email.setSSL(true);
		
			
			
		try {
				
			String newPassword = newpass;	
			
			

			
				email.setFrom("artlinkrecovery@gmail.com");
				email.setSubject("Recuperação de Password da sua conta Artlink");
				email.setMsg("A sua nova password:  "
						+ newPassword + "   :Se não efectuou este pedido por favor contacte a administração da Artlink");
				email.addTo(usermail);
				email.send();
				
				
				
			
		
			}catch(EmailException e) {
				e.printStackTrace();
			 }
			

		
		
			
		}else {
			
			return "Conta nao existe o email";		
				}
		
	return "enviado pedido";
	}



	public ArrayList<Country> getCountry() {
		
		return (ArrayList<Country>) countryrep.findAll();
		
	}
	


	
	public String addWatching(String hash,String iduser) {
		
		Optional<User> user = userRep.findByHashes(hash);
		Optional<User> user2 = userRep.findById(iduser);
		
		
		if(user.isPresent() && user2.isPresent()) {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();        
			// Using DateFormat format method we can create a string 
			// representation of a date with the defined format.
			String reportDate = df.format(today);
			
			
			
			boolean check = false;
			boolean check1 = false;
			
			
			

			
			if(!user.get().getWatching().isEmpty() && !user2.get().getWatched().isEmpty()) {
				
				for(Watch w : user.get().getWatching()) {
					if(w.getIduser().compareTo(iduser)==0) {
						check=true;
					
					}
					if(check) {
						user.get().getWatching().remove(w);
						break;
					}
				}
				for(Watch w : user2.get().getWatched()) {
					if(w.getIduser().compareTo(user.get().getId())==0) {
						check1=true;
					
					}
					if(check1) {
						user2.get().getWatched().remove(w);
						break;
					}
				}
			}
			
			
			
			if(!check && !check1) {
				user.get().getWatching().add(new Watch(iduser, reportDate,user2.get().getFirstname()+" "+user2.get().getLastname(),user2.get().getPathimage()));
				user2.get().getWatched().add(new Watch(user.get().getId(), reportDate,user.get().getFirstname()+" "+user.get().getLastname(),user.get().getPathimage()));
				
				userRep.save(user.get());
				userRep.save(user2.get());
				
				return "aceite";
			}else {
				userRep.save(user.get());
				userRep.save(user2.get());
			}
			
			
		}
		
		return "naceite";
		
	}
	

public String applyEventEmail(String receptor,String artista) {
		
	
		
	

		
	
			
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthentication("artlinkrecovery@gmail.com", "thalia2018");
			//email.setAuthentication("admin@artlink.pt","thaliapt18");
			//email.setHostName("webdomain02.dnscpanel.com");
			//email.setHostName("pop.sapo.pt ");
			//email.setSmtpPort(110);
			//email.setAuthentication("apoio.artlink@sapo.pt", "Thalia2018");
			email.setSSL(true);
		
			
			
		try {
				
	
				email.setFrom("artlinkrecovery@gmail.com");
				email.setSubject("Tem um artista interessado no seu Evento!");
				email.setMsg("O Artista " +artista+
						   " Encontra-se interessado no Seu evento");
				email.addTo(receptor);
				email.send();
							
				System.out.println("email enviado para: "+ receptor +"");
			
				
			
		
			}catch(EmailException e) {
				e.printStackTrace();
			 }
			
 
			
			return "Conta nao existe o email";		

	}
	
	
}
