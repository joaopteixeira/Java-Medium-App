package JV.DB.Service;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import JV.DB.model.Country;

public interface CountryRepository extends MongoRepository<Country, String> {
	
	
	Optional<Country> findByName(String name);
	
}
