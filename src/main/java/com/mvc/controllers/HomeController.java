package com.mvc.controllers;

import java.sql.Blob;
import java.util.Base64;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvc.data.Flower;
import com.mvc.data.FlowerRepository;

@Controller
public class HomeController {
	
	@Autowired
	FlowerRepository repo;
	@RequestMapping(value="/")
	public String goHome(Model model, Pageable pageable) throws Exception {
	Collection<? extends GrantedAuthority> role=SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	System.out.println(role);

	Page<Flower> flowers=repo.findAll(pageable);
	for (Flower flower : flowers) {
		Blob image=flower.getImage();
		int bloblength=(int) image.length(); 
		byte[] b=image.getBytes(1, bloblength);
		String baseImage =Base64.getEncoder().encodeToString(b);
		flower.setBase64image(baseImage);
	    }
		model.addAttribute("page", flowers);
		if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
			return "home";
			
		} else {
			for(GrantedAuthority r:role) {
				if(r.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) {
					return "redirect:/admin/home";
				}else{
					
					return "home";
				}
				
				}
		}
		
		return "home";
			
		}
	

		@RequestMapping(value="/login", method=RequestMethod.GET)
		public String login() {
			return "login";
			}
		@RequestMapping(value="/confirm")
		public String confirm() {
			return "confirm";
			}
		
		
	}

