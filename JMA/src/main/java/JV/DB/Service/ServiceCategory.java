package JV.DB.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JV.DB.model.Category;

@Service("ServiceCategory")
public class ServiceCategory{

	@Autowired
	CategoryRepository repository;
	
	
	public ServiceCategory() {
		super();
		
		
	}

/*
	public void mockupdata() {
		Category category = new Category("Musico");
		
		category.getSubCategory().add(new Category("Baterista"));
		category.getSubCategory().add(new Category("Baixista"));
		category.getSubCategory().add(new Category("Guitarrista"));
		category.getSubCategory().add(new Category("Vocalista"));
		
		
		Category categoryr = new Category("Representação");
		
		categoryr.getSubCategory().add(new Category("Teatro"));
		categoryr.getSubCategory().add(new Category("Televisão"));
		categoryr.getSubCategory().add(new Category("Cinema"));
		categoryr.getSubCategory().add(new Category("Figurante"));
		
		Category categoryb = new Category("Banda");
		
		
		repository.save(category);
		repository.save(categoryr);
		repository.save(categoryb);
		
		
	}
	
	*/
}
