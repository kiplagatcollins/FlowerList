package com.mvc.controllers;

import java.sql.Blob;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.data.Farmer;
import com.mvc.data.FarmerRepository;
import com.mvc.data.UserRepository;
import com.mvc.data.Users;

@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {
	@Autowired
	UserRepository repo;
	@Autowired
	FarmerRepository fam;
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String registerUser() {
		return "register";
		
	}
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String saveUser(@ModelAttribute Users user,Model model) {
		
		
		if (user.getRole().equals("customer")) {
			user.setRole("ROLE_CUSTOMER");
			
		} else{
			//user.setRole("ROLE_FARMER");
			repo.save(user);
			model.addAttribute("user", user);
			return "confirmform";

		}
		repo.save(user);
		Authentication auth= new UsernamePasswordAuthenticationToken(user, user.getPassword(),user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		return "redirect:/";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
		
	}
	@RequestMapping(value="/update")
	public String update() {
		return "register";
	}
	

	@RequestMapping(value = "/save",method=RequestMethod.POST)
	public String confirmform(@ModelAttribute Farmer farmer,@ModelAttribute ("user") Users user ) throws Exception {
		System.out.println(user);
		farmer.setUserId(user.getUserId());
		MultipartFile file=farmer.getFile();
	    byte[] ibyte=file.getBytes();	
	    Blob blob=new SerialBlob(ibyte);
	    farmer.setImage(blob);
	    farmer.setFarmersName(user.getFirstname()+" "+user.getLastName());
		fam.save(farmer);
		return "redirect:/";
	}
}
