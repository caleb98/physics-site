package com.physicssite.backend.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.physicssite.backend.dto.ServerResponse;

@Controller
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_USERNAME_REGEX = 
			Pattern.compile("^[A-Za-z0-9]*$");
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/login")
	public String loginPage(HttpServletRequest request) {
		return "login.html";
	}
	
	@GetMapping(path="/register")
	public String registerPage() {
		return "register.html";
	}
	
	@PostMapping(path="/register")
	public @ResponseBody ServerResponse addNewUser(
			@RequestParam String username, 
			@RequestParam String email, 
			@RequestParam String password,
			@RequestParam String passwordConfirm) 
	{
		
		// Check email/username availability
		if(userRepository.findByEmail(email) != null) {
			return new ServerResponse("A user with that email already exists.", true);
		}
		else if(userRepository.findByUsername(username) != null) {
			return new ServerResponse("A user with that username already exists.", true);
		}
		
		// Check username length
		if(username.length() < 8 || username.length() > 25) {
			return new ServerResponse("Username must be 8 to 25 characters long.", true);
		}
		
		// Check username characters
		Matcher usernameMatcher = VALID_USERNAME_REGEX.matcher(username);
		if(!usernameMatcher.matches()) {
			return new ServerResponse("Username contains an invalid character. Only letters and numbers may be used.", true);
		}
		
		// Validate email
		Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		if(!emailMatcher.matches()) {
			return new ServerResponse("Invalid email.", true);
		}
		
		// Validate password
		if(!password.equals(passwordConfirm)) {
			return new ServerResponse("Your passwords do not match.", true);
		}
		
		if(password.length() < 10 || password.length() > 75) {
			return new ServerResponse("Password must be 10 to 75 characters in length.", true);
		}
		
		// Hash the password
		BCryptPasswordEncoder encoder = context.getBean(BCryptPasswordEncoder.class);
		String encodedPassword = encoder.encode(password);
		
		// Create the user
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPasswordHash(encodedPassword);
		userRepository.save(user);
		
		// Success!
		return new ServerResponse("Success!");
	}
	
}
