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

import JV.DB.Service.CountryRepository;
import JV.DB.model.Country;
import JV.DB.model.District;

@RestController
@RequestMapping("/mcountry")
public class RestCountry {
	
	@Autowired
	CountryRepository countrep;
	
	@GetMapping("/getcountrybyname")
	public ResponseEntity<?> getCountrybyName(@RequestParam(name="hash",defaultValue="") String hash, @RequestParam("name") String name){
		
		Optional<Country> country = countrep.findByName(name);
		
		if(country != null) {
			return new ResponseEntity<Optional<Country>>(country,HttpStatus.OK);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
	}
	
	@GetMapping("/getdistrictbycountryname")
	public ResponseEntity<?> getDistrictbyCountryName(@RequestParam("name") String name){
		
		Optional<Country> country = countrep.findByName(name);
		
		if(country != null) {
			List<District> districts = country.get().getDistricts();
			return new ResponseEntity<List<District>>(districts,HttpStatus.OK);
		}
		
		return new ResponseEntity<>("null",HttpStatus.OK);
	}

}
