package JV.DB.Service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import JV.DB.model.Notify;

public interface NotifyRepository extends MongoRepository<Notify, String> {
	
	List<Notify> findByUseridAndEstado(String userid,int estado);

	List<Notify> findByUseridAndTypeNotAndUserdoNot(String userid,int type,String userdo);
	
}
