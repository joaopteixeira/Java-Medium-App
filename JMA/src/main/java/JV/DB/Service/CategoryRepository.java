package JV.DB.Service;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import JV.DB.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

	Optional<Category> findByDescription(String description);
	
}
