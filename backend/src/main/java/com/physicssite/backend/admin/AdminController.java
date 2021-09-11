package com.physicssite.backend.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

	@GetMapping(path="/admin")
	public String adminPage(HttpServletRequest request) {
		return "admin.html";
	}
	
}
