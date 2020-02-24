package JV.DB.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import JV.DB.model.Post;
import JV.DB.model.User;

public interface PostRepository extends MongoRepository<Post, String> {
	
	
	Optional<Post> findById(String id);
	Page<Post> findAll(Pageable pageable);
	List<Post> findByIduser(String id);
	List<Post> findByMediaTypemediaAndIduser(int typemidia,String iduser);
	

	
	


}
