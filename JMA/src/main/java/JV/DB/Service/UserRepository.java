package JV.DB.Service;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import JV.DB.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	
	Optional<User> findById(String id);
	List<User> findByDistrict(String district);
	List<User> findByFirstnameContaining(String firstname);
	List<User> findByCountry(String country);
	Optional<User> findByEmail(String email);
	Optional<User> findByHashes(String hashes);
	List<User> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname);
	List<User> findByDistrictAndCategoryAndSubcategory(String district,String category,String Subcategory);
	List<User> findByDistrictAndCategory(String district,String category);
	List<User> findByCategory(String category);
	List<User> findByCategoryAndSubcategory(String category,String subcategory);
	//List<User> findByDistrict(String district);

}
