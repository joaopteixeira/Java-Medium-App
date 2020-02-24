package JV.DB.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import JV.DB.Functions.FCategory;
import JV.DB.Functions.FFeed;
import JV.DB.Functions.FPost;
import JV.DB.Functions.FUser;
import JV.DB.Service.CategoryRepository;
import JV.DB.Service.FileStorageService;
import JV.DB.Service.UserRepository;
import JV.DB.Util.DateUtil;
import JV.DB.model.Category;
import JV.DB.model.Comment;
import JV.DB.model.Media;
import JV.DB.model.Post;
import JV.DB.model.User;

@Controller
public class WebHome {
	
	@Autowired
	FPost  fpost;
	
	@Autowired
	FUser  fuser;
	
	@Autowired
	UserRepository userrep;;
	
	@Autowired
	CategoryRepository catrep;
	@Autowired
	FFeed ffeed;
	
	 @Autowired
	 private FileStorageService fileStorageService;
	
	@GetMapping("/index")
	public String index() {
		

		return "index.html";
	}
	
	@GetMapping("/feed")
	public String feed(Model page,@RequestParam("personid") String personid,@RequestParam("main") String main,@RequestParam("frag") String frag,@RequestParam(value="keyword",defaultValue="*") String keyword,HttpSession session){
		

		
		User u = (User)session.getAttribute("User");
		
		Optional<User> user = userrep.findById(u.getId());
		
		page.addAttribute("personid","you");
		if (user.isPresent()) {



			page.addAttribute("User", user.get());
			page.addAttribute("main", main);
			page.addAttribute("frag", frag);
			session.setAttribute("User", user.get());
			page.addAttribute("friends", ffeed.getFriends(user.get().getId()));
			
			
			if(main.compareTo("chat")== 0) {
				page.addAttribute("User", (User) session.getAttribute("User"));
				page.addAttribute("main", "chat");
				return "feedmain.html";
				
			}


			if (main.compareTo("perfil") == 0) {
				
				
				page.addAttribute("User", (User) session.getAttribute("User"));
				
				Optional<User> persona = userrep.findById(personid);
							
				
				List posts = (List) fpost.getPostsByUser(persona.get().getId());
				
	
				
				page.addAttribute("Person",persona.get());
				page.addAttribute("posts",posts);
				page.addAttribute("personid",personid);
		

				if (frag.compareTo("timeline") == 0) {

					
					page.addAttribute("User", (User) session.getAttribute("User"));
								


					page.addAttribute("frag", "timeline");

					return "feedmain.html";

				}else
					
				
				
				if (frag.compareTo("about") == 0) {

			
					page.addAttribute("frag", "about");

					return "feedmain.html";

				}else
				
				if (frag.compareTo("galeria") == 0) {

					
					page.addAttribute("User", (User) session.getAttribute("User"));
				
					
					
					
					
					page.addAttribute("posts",posts);
					page.addAttribute("frag", "galeria");

					return "feedmain.html";

				}else
				
				if (frag.compareTo("playlist") == 0) {

			
					page.addAttribute("frag", "playlist");	
					
					page.addAttribute("posts",posts);

					return "feedmain.html";

				}else
				
				if (frag.compareTo("amigos") == 0) {

				
					page.addAttribute("frag", "amigos");

					return "feedmain.html";

				}else
				
				if (frag.compareTo("profile") == 0) {

			
					page.addAttribute("frag", "profile");
					page.addAttribute("Category", catrep.findAll());

					return "feedmain.html";

				}else
				if (frag.compareTo("password") == 0) {

					
					page.addAttribute("frag", "password");
					
					

					return "feedmain.html";

				}
				
				

			}else if (main.compareTo("homepage") == 0) {

				if (frag.compareTo("post") == 0) {

					page.addAttribute("frag", "post");
					System.out.println(user.get().getId());
					page.addAttribute("posts", fpost.getPost(user.get().getId(), 0, 0));

					return "feedmain.html";

				}else

				if (frag.compareTo("chat") == 0) {

					page.addAttribute("frag", "chat");
					page.addAttribute("User", (User) session.getAttribute("User"));
					return "feedmain.html";

				}else

				if (frag.compareTo("contacts") == 0) {

					page.addAttribute("frag", "contacts");
					page.addAttribute("User", (User) session.getAttribute("User"));
					return "feedmain.html";

				}else

				if (frag.compareTo("search") == 0) {

			
					List<Category> categories = catrep.findAll();
					page.addAttribute("categories", categories);
					
				
				
					
					if(keyword.compareTo("*")!=0) {
						
						page.addAttribute("personid","you");
						
						List<User> results = fuser.getUserContainig(keyword);

						page.addAttribute("results",results);
					}
					

				}
				

				else if(frag.compareTo("doevents") == 0) {
					  page.addAttribute("User", (User)session.getAttribute("User"));
						page.addAttribute("personid","you");
						page.addAttribute("Category", catrep.findAll());
				  }
				
				else if(frag.compareTo("frageventlist") == 0) {
					  page.addAttribute("User", (User)session.getAttribute("User"));
						page.addAttribute("personid","you");
				  }
				

				return "feedmain.html";
				
				
			}
			
				
		}

		return "redirect:/index";

	}
	
	@PostMapping("/newpost")
  public String feed(@RequestParam("file") MultipartFile file,@RequestParam("content") String content,@RequestParam("video") String video,  @RequestParam("typemedia") int typemedia,/*@RequestParam("pathfile") String pathfile,*/ HttpSession session){
//		
//		
		String fileName = fileStorageService.storeFile(file,FilenameUtils.getExtension(file.getOriginalFilename()));
		String fileDownloadUri;
		//String fileName = rand.replace("-", "");

        if(typemedia != 1) {
        fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("upload/downloadFile/")
                .path(fileName)
                .toUriString();     
		}else {
	    	fileDownloadUri = video;
	    }
        System.out.println(fileDownloadUri);
		
		
		User u = (User)session.getAttribute("User");
//		
		Post p = new Post();
//		
//		
		LocalDateTime date =  LocalDateTime.now();  
//
//		
			String username = u.getUsername() ;
//		
			String iduser;
//		
			Media media = new Media(typemedia,fileDownloadUri);//pathfile);
//		
//		
	    p.setContent(content);
		p.setUsername(username);
		p.setIduser(u.getId());
		p.setDate(date.toString());	
		p.setMedia(media);
		fpost.newPost(p);
		
//		
	
	 return "redirect:/feed?main=homepage&personid=you&frag=post";
	}
//	
	
	
@PostMapping("/newcomment")
public String newComment(@RequestParam("content") String content,@RequestParam("idpost") String idpost, HttpSession session){
	
	System.out.println("Id-POST:  "+idpost);
	System.out.println("Content:  "+content);
	
	User u = (User)session.getAttribute("User");
	
	String hash = (String)session.getAttribute("hash");
	System.out.println("HASH:  "+hash);

	Post p = fpost.addComment(idpost,hash,content,UUID.randomUUID().toString().replace("-", ""));

	
	
	
  return "redirect:/feed?main=homepage&personid=you&frag=post";
}


@PostMapping("/newsearch")	
public String NewSearch(Model page,@RequestParam("personid") String personid,@RequestParam("keyword") String keyword, HttpSession session) {
	
	
	page.addAttribute("User",(User)session.getAttribute("User"));

	page.addAttribute("main", "homepage");
	page.addAttribute("frag", "perfil");

	  return "redirect:/feed?main=homepage&personid=you&frag=search&keyword="+keyword;
}

@GetMapping("/chat")	
public String chat(Model page,@RequestParam("personid") String personid, HttpSession session) {
	
	
	page.addAttribute("User",(User)session.getAttribute("User"));

	page.addAttribute("main", "chat");


	  return "redirect:/feed?main=chat&personid=you&frag=chat";
}



@GetMapping("/profile")	
public String profile(Model page, HttpSession session,@RequestParam("personid") String personid) {
	
	
	page.addAttribute("User",(User)session.getAttribute("User"));
	
	User u = (User)session.getAttribute("User");
	
	
	

	page.addAttribute("main", "perfil");
	page.addAttribute("frag", "timeline");
	

	
	
	

	  return "redirect:/feed?main=perfil&frag=timeline&personid="+personid;
}




}
