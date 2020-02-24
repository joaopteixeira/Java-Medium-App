package JV.DB.Functions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JV.DB.Service.CategoryRepository;
import JV.DB.model.Category;
@Service("fcategory")
public class FCategory {

	@Autowired
	CategoryRepository crep;
	
	
	public List<Category> getCategorys(){
		
		return crep.findAll();
	}
	
	
	
	/*
	public void mockupdata() {
		
		Category c = new Category("Musica");
		c.getSubCategory().add(new Category("Cantor"));
		c.getSubCategory().add(new Category("Guitarrista"));
		c.getSubCategory().add(new Category("Baterista"));
		c.getSubCategory().add(new Category("Baixista"));
		Category c1 = new Category("Teatro");
		c1.getSubCategory().add(new Category("Ator"));
		c1.getSubCategory().add(new Category("Figurante"));
		c1.getSubCategory().add(new Category("Encenador"));
		Category c2 = new Category("Cinema");
		c2.getSubCategory().add(new Category("Ator"));
		c2.getSubCategory().add(new Category("Figurante"));
		c2.getSubCategory().add(new Category("Realizador"));
		Category c3 = new Category("Arte");
		c3.getSubCategory().add(new Category("Pintor"));
		c3.getSubCategory().add(new Category("Escultor"));
		
		crep.save(c);
		crep.save(c1);
		crep.save(c2);
		crep.save(c3);
	}*/
	
	
}
