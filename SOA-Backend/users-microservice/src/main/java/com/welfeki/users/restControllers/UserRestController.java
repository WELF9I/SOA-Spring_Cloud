package com.welfeki.users.restControllers;

import java.util.List;

import com.welfeki.users.repos.UserRepository;
import com.welfeki.users.service.register.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.welfeki.users.entities.User;
import com.welfeki.users.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	UserRepository userRep;

	@Autowired
	UserService userService;

	@RequestMapping(path = "all",method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userRep.findAll();
	}


	@PostMapping("/register")
	public User register(@RequestBody RegistrationRequest request) {
		return userService.registerUser(request);

	}


	@GetMapping("/verifyEmail/{token}")
	public User verifyEmail(@PathVariable("token") String token){
		return userService.validateToken(token);
	}


}