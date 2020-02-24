
package JV.DB.Service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import JV.DB.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {
	
	List<Event> findByDistrictName(String name);
	List<Event> findByIduser(String iduser);
	
	

}
