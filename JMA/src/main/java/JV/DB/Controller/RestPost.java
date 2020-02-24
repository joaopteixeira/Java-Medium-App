package JV.DB.Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.annotation.MultipartConfig;

import org.apache.commons.io.FilenameUtils;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.mongodb.util.JSON;

import JV.DB.Functions.FPost;
import JV.DB.Service.FileStorageService;
import JV.DB.Service.PostRepository;
import JV.DB.Service.UserRepository;
import JV.DB.UploadFile.UploadFileResponse;
import JV.DB.model.Comment;
import JV.DB.model.Post;
import JV.DB.model.User;


@RestController
@RequestMapping("mposts")
public class RestPost {

	@Autowired
    private FileStorageService fileStorageService;
	
	
	@Autowired
	PostRepository postrepo;

	
	@Autowired
	UserRepository urep;
	
	@Autowired
	FPost ffpost;
	
	@RequestMapping("/get")
	public ResponseEntity<?> getPosts(@RequestParam("iduser") String iduser,@RequestParam("page") String page,@RequestParam("size") String size) {
		
		Optional<User> user = urep.findById(iduser);
		
		try {
			
			if(user.isPresent()) {
				List<Post> aux = ffpost.getPost(iduser, Integer.valueOf(page),Integer.valueOf(size));
				
				return new ResponseEntity<>((aux.size()==0?"null":aux),HttpStatus.OK);
			}
			
		}catch(NumberFormatException e) {
			return new ResponseEntity<String>("null",HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("null",HttpStatus.OK);
		
		
	}
	
	
	@GetMapping("/getpostimage")
	public ResponseEntity<?> getPostImage(@RequestParam("hash") String hash,@RequestParam("iduser") String iduser){
		
		List<Post> c = ffpost.getPostImage(hash,iduser);
		
		if(c!=null) {
			return new ResponseEntity<>(c,HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
		
	}
	
	@GetMapping("/getpostbyid")
	public ResponseEntity<?> getpostbyid(@RequestParam("hash") String hash,@RequestParam("idpost") String idpost){
	
		Optional <Post> post = postrepo.findById(idpost);
		
		Post p1 =  post.get();
		
		if(post.isPresent()) {
			Optional<User> u1 = urep.findById(p1.getIduser());
			 p1.setUserwatched(u1.get().getWatched().size());
			 p1.setUsername(u1.get().getFirstname()+" "+u1.get().getLastname());
			 p1.setUserimage(u1.get().getPathimage());
			 ArrayList<Comment> comments = new ArrayList<>();
			 for(Comment c:p1.getComments()) {
				 Optional<User> usercom = urep.findById(c.getIduser());
				 c.setUsername(usercom.get().getFirstname()+" "+usercom.get().getLastname());
				 c.setImguser(usercom.get().getPathimage());
				 comments.add(c);
				 
				 p1.getComments().set(p1.getComments().indexOf(c), c);
			 }
			 
			 
			 
			return new ResponseEntity<>(p1,HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
	
	}
	
	
	
	@GetMapping("/getpostbyiduser")
	public ResponseEntity<?> getPostByIdUser(@RequestParam("hash") String hash,@RequestParam("iduser") String iduser){
		
		List<Post> c = ffpost.getPostById(hash,iduser);
		
		if(c!=null) {
			return new ResponseEntity<>(c,HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
		
	}
	@GetMapping("/getpostsound")
	public ResponseEntity<?> getPostSound(@RequestParam("hash") String hash,@RequestParam("iduser") String iduser){
		
		List<Post> c = ffpost.getPostSound(hash,iduser);
		
		if(c!=null) {
			return new ResponseEntity<>(c,HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
		
	}

	
	

	
	
	@GetMapping("/addcomment")
	public ResponseEntity<?> addComment(@RequestParam("hash") String hash,@RequestParam("idpost") String idpost,@RequestParam("content") String content,@RequestParam(value="tag", defaultValue="null") String tag){
		
		if(tag.compareTo("null")==0) {
			
			tag=UUID.randomUUID().toString().substring(0, 8).replace("-", "");
		}
		
		Post p = ffpost.addComment(idpost, hash, content,tag);
		
		if(p!=null) {
			return new ResponseEntity<>(p,HttpStatus.ACCEPTED);
			}
		
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
		
	}
	
	@GetMapping("/getcomment")
	public ResponseEntity<?> getComment(@RequestParam("hash") String hash,@RequestParam("idcomment") String idcomment){
		
		Comment c = ffpost.getComment(hash, idcomment);
		
		if(c!=null) {
			return new ResponseEntity<>(c,HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
		
	}
	

	@GetMapping("/getcommentbysize")
	public ResponseEntity<?> getCommentBySize(@RequestParam("hash") String hash,@RequestParam("idpost") String idpost,@RequestParam("size") String size){
		
		Post p = ffpost.getPostBySizeComment(hash, idpost, Integer.valueOf(size));
		
		if(p!=null) {
			return new ResponseEntity<>(p.getComments(),HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
		
	}
	
	@GetMapping("/getcomments")
	public ResponseEntity<?> getComments(@RequestParam("hash") String hash,@RequestParam("idpost") String idpost){
		
		List<Comment> comments = ffpost.getComments(idpost, hash);
		
		if(comments!=null) {
			return new ResponseEntity<>(comments,HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
		
		
		
	}
	
	
	@PostMapping("/addpost")
	public ResponseEntity<String> addPost(@RequestBody Post post){
		
		return new ResponseEntity<>(ffpost.newPost(post),HttpStatus.OK);
		
	}
	
	@GetMapping("/like")
	public ResponseEntity<String> like(@RequestParam("id_post") String id_post, @RequestParam("id_user") String id_user){
		
		ffpost.like(id_user, id_post);
		
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
	
	@GetMapping("/removefile")
	public ResponseEntity<String> removefile(@RequestParam("hash") String hash, @RequestParam("path") String path){
		
		Optional<User> user = urep.findByHashes(hash);
		
		if(user.isPresent()) {
			
			fileStorageService.removeFile(path);
			
		}
		
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
	
	
	@PostMapping("/upload")
	public UploadFileResponse uploadPost(@RequestParam("file") MultipartFile file,@RequestParam("hash") String hash){
		
		System.out.println(hash);
		
		Optional<User> user = urep.findByHashes(hash);
		
		if(user.isPresent()) {
			String fileName = fileStorageService.storeFile(file,FilenameUtils.getExtension(file.getOriginalFilename()));
			
			System.out.println(file.getOriginalFilename());
	    	

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("upload/downloadFile/")
	                .path(fileName)
	                .toUriString();     
	        System.out.println(fileDownloadUri);
	        
	        
	        return new UploadFileResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize());
			
		}
		
		
		return null;

	}
	
	
	
	
	@GetMapping("/allposts")
	public ResponseEntity<?> getPosts(@RequestParam("iduser") String iduser, @RequestParam("idpost") String idpost,@RequestParam("tipo") int tipo,@RequestParam("sizepost") int sizepost,@RequestParam("page") int page,@RequestParam("range") int range) {
		String dateStart;
		String dateStop;
		String ano, mes, dia, hora, minuto, segundo;
		int sizeposts = 0, q = 0, size, temp=0;
		int userwatching = 0;
		
		ArrayList<Post> tpost = (ArrayList<Post>) ffpost.findAllposts();
		ArrayList<Post> posts = new ArrayList<>();
		
		 Collections.sort(tpost, new Comparator<Post>() {
             @Override
             public int compare(Post o1, Post o2) {

                 Date d1 = null;
                 Date d2 = null;
                 DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                 try {
                     d1 = df.parse(o1.getDate());
                     d2 = df.parse(o2.getDate());

                 } catch (ParseException e) {
                     e.printStackTrace();
                 }

                 return d2.compareTo(d1);
             }
         });
		 DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		 Date today = Calendar.getInstance().getTime();        
		 String reportDate = df.format(today);
		 for(Post p : tpost) {
			 	
			 	 dateStart = p.getDate();
				 dateStop = reportDate;


				Date d1 = null;
				Date d2 = null;

				try {
					d1 = df.parse(dateStart);
					d2 = df.parse(dateStop);

					long diff = d2.getTime() - d1.getTime();

					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffHours = diff / (60 * 60 * 1000) % 24;
					long diffDays = diff / (24 * 60 * 60 * 1000);
					long  diffYear = diffDays/365;
					long  diffMounth = diffDays/30;;
					
					/*System.out.print(diffYear + " year, ");
					System.out.print(diffMounth + " mounth, ");
					System.out.print(diffDays + " days, ");
					System.out.print(diffHours + " hours, ");
					System.out.print(diffMinutes + " minutes, ");
					System.out.print(diffSeconds + " seconds.");*/
					
					ano = String.valueOf(diffYear);
					mes = String.valueOf(diffMounth);						
					dia = String.valueOf(diffDays);
					hora = String.valueOf(diffHours);
					minuto = String.valueOf(diffMinutes);
					segundo = String.valueOf(diffSeconds);

					
					if(diffDays != 0) {
						if(diffDays >= 30) {
							if(diffDays >= 365) {
								if(diffYear == 1) {
									p.setDate(ano+" ano atrás");
								}else {
									p.setDate(ano+" anos atrás");
								}
							}else {
								if(diffMounth == 1) {
									p.setDate(mes+" mês atrás");
								}else {
									p.setDate(mes+" meses atrás");
								}
							}
							
						}else {
							if(diffDays == 1) {
								p.setDate(dia+" dia atrás");
							}else {
								p.setDate(dia+" dias atrás");
							}
						}
					}else {
						if(diffHours != 0) {
							if(diffHours == 1) {
								p.setDate(hora+" hora atrás");
							}else {
								p.setDate(hora+" horas atrás");
							}
						}else {
							if(diffMinutes != 0) {
								if(diffMinutes == 1) {
									p.setDate(minuto+" minuto atrás");
								}else {
									p.setDate(minuto+" minutos atrás");
								}
							}else {
								if(diffSeconds == 1) {
									p.setDate(segundo+" segundo atrás");
								}else {
									p.setDate(segundo+" segundos atrás");
								}
							}
						}
					}

				 } catch (Exception e) {
					e.printStackTrace();
				 }

			  }
		
		 if(tipo != 2) {
			 sizeposts = tpost.size();
		 }else {
			 for(Post p : ffpost.findAllposts()) {
				 if(p.getIduser().compareTo(iduser)==0) {
					 sizeposts +=1;
				 }
			 }
		 }
		 q = sizeposts - sizepost;
		 size = (q + (range*page));
		 if(sizeposts <= sizepost) {
			 q = 0;
		 }
		 
		 
		if(tipo == 0) {
			for(Post p:tpost) {
				if(temp >= size && temp < size+range) {
					for(User u: urep.findAll()) {
						if(u.getId().compareTo(p.getIduser())==0) {
							userwatching =u.getWatched().size();	
						}
					}
					p.setUserwatched(userwatching);
					posts.add(p);
				}
				temp +=1;
			}
		}else if(tipo == 1) {
			for(Post p:tpost) {
				if(p.getId().equals(idpost)) {
					for(User u: urep.findAll()) {
						if(u.getId().compareTo(p.getIduser())==0) {
							userwatching =u.getWatched().size();	
						}
					}
					p.setUserwatched(userwatching);
					posts.add(p);
				}
			}
		}else if(tipo == 2) {
			for(Post p:tpost) {
				if(p.getIduser().equals(iduser)) {
					if(temp >= size && temp < size+range) {
						for(User u: urep.findAll()) {
							if(u.getId().compareTo(p.getIduser())==0) {
									userwatching =u.getWatched().size();	
								}
							}
							p.setUserwatched(userwatching);
							posts.add(p);
						}
					}
				}
			}
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	


	@GetMapping("/finduser")
	public ResponseEntity<?> getuser(@RequestParam("iduser") String iduser) {
		
		ArrayList<User> users = new ArrayList<>();

			for(User u:urep.findAll()) {
				if(u.getId().equals(iduser)) {
					users.add(u);
				}
			}
		
		return new ResponseEntity<>(users,HttpStatus.OK);
	}


	@GetMapping("/getlikes")
	public ResponseEntity<?> getlikes(@RequestParam("id_post") String id_post) {
		ArrayList<Post> posts = new ArrayList<>();
			for(Post p:ffpost.findAllposts()) {
				if(p.getId().equals(id_post)) {
					posts.add(p);
				}
			}
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}

	@GetMapping("/sizeposts")
	public ResponseEntity<String> getSize(){
		int size=0;
		size = ffpost.findAllposts().size();
	
		return new ResponseEntity<String>(""+size,HttpStatus.OK);
	}
	
	@GetMapping("/sizeidposts")
	public ResponseEntity<String> getSizePost(@RequestParam("iduser") String iduser){
		int size=0;
		
		for(Post p : ffpost.findAllposts()) {
			if(p.getIduser().compareTo(iduser)==0) {
				size +=1;
			}
		}
	
		return new ResponseEntity<String>(""+size,HttpStatus.OK);
	}	
	
	
}
