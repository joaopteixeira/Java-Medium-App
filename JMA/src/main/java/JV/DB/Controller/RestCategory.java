package JV.DB.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JV.DB.Functions.FCategory;
import JV.DB.Service.CategoryRepository;
import JV.DB.model.Category;

@RestController
@RequestMapping("mcategory")
public class RestCategory {

	
	@Autowired
	FCategory fcategory;
	
	@Autowired
	CategoryRepository catrep;
	
	@GetMapping("/getcategory")
	public List<Category> getCategorys(){
		
		return fcategory.getCategorys();
	}
	

	@GetMapping("/getcategorybyname")
	public ResponseEntity<?> getCategoryName(@RequestParam(name="hash",defaultValue="") String hash, @RequestParam("description") String description){
		
		Optional<Category> c = catrep.findByDescription(description);
		
		if(c != null) {
			List<Category> subcats = c.get().getSubCategory();
			
			if(!subcats.isEmpty()) {
			return new ResponseEntity<List<Category>>(subcats,HttpStatus.OK);
			}
			
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
	}
	/*
	@GetMapping("/mockupdata")
	public void mockupdata(){
		
		fcategory.mockupdata();
	}*/
}
