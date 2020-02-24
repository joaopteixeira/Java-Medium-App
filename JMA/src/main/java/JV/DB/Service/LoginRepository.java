package JV.DB.Service;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import JV.DB.model.Login;

public interface LoginRepository extends MongoRepository<Login, String>{
	
	Optional<Login> findByEmailAndPassword(String email,String password);
	Optional<Login> findByEmail(String email);
	


}
