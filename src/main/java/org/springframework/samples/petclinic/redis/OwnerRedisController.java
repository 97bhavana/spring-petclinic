package org.springframework.samples.petclinic.redis;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.samples.petclinic.redis.OwnerRedis;
import org.springframework.samples.petclinic.redis.OwnerRedisRepository;
import org.springframework.samples.petclinic.redis.OwnerRedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;


@Controller
@RestController
public class OwnerRedisController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	private final OwnerRedisRepository ownerRedisrepository;

	private VisitRepository visits;

	public OwnerRedisController(OwnerRedisRepository clinicService, VisitRepository visits) {
		this.ownerRedisrepository = clinicService;
		this.visits = visits;
	}
	
	@Autowired
	public OwnerRedisController(OwnerRedisRepository userRepository) {
        this.ownerRedisrepository = userRepository;
    }

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	//@Cacheable(value = "ownerpetlist")
	@GetMapping("/ownersredis")
	//@GetMapping("/ownersredis.html")
	public String processFindForm(OwnerRedis owner, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<OwnerRedis> results = this.ownerRedisrepository.findByLastName(owner.getLastName());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwnersRedis";
		}
		else if (results.size() == 1) {
			// 1 owner found
			owner = results.iterator().next();
			return "redirect:/ownersredis/" + owner.getId();
		}
		else {
			// multiple owners found
			model.put("selections", results);
			return "owners/ownersRedisList";
			//return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
	}
	
}
