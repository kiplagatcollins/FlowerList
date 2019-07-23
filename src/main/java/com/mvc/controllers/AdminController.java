package com.mvc.controllers;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvc.data.Farmer;
import com.mvc.data.FarmerRepository;
import com.mvc.data.Flower;
import com.mvc.data.FlowerRepository;
import com.mvc.data.Monthly;
import com.mvc.data.OrderList;
import com.mvc.data.OrderListRepository;
import com.mvc.data.Orders;
import com.mvc.data.UserRepository;
import com.mvc.data.Users;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	OrderListRepository repo;

	@Autowired
	UserRepository repo1;
	@Autowired
	FarmerRepository fam;
	@Autowired
	FlowerRepository flo;
	@Autowired
	OrderListRepository ord;


	@RequestMapping(value = "/home")
	public String home() {
		return "adminhome";
	}



	@RequestMapping(value = "/confirm",method=RequestMethod.GET)
	public String confirm(Model model) throws SQLException {
		List<Farmer> farmers=fam.findByConfirmed(0);
		for(Farmer f:farmers) {
			Blob image=f.getImage();
			int bloblength=(int) image.length(); 
			byte[] b=image.getBytes(1, bloblength);
			String baseImage =Base64.getEncoder().encodeToString(b);
			f.setBase64image(baseImage);
			
		}
		model.addAttribute("farmers", farmers);
		System.out.println(farmers);
		return "confirmfarmers";
	}
	@RequestMapping(value = "/confirmsave/{id}")
	public String confirmfarmer(Model model,@PathVariable String id) {
		Users user=repo1.findOne(id);
		user.setRole("ROLE_FARMER");
		repo1.save(user);
		Farmer f=fam.findByUserId(id);
		System.out.println(f);
		f.setConfirmed(1);
		fam.save(f);
		return "redirect:/admin/confirm";
	}
	@RequestMapping(value="/list")
	public String lists(Model model) {
		List<OrderList> orders=repo.findAll();
		model.addAttribute("orders", orders);
		return "orderlist";
	}
	@RequestMapping(value="/view/{id}")
	public String viewOrders(Model model,@PathVariable int id,OrderList orderlist) {
    	orderlist=repo.findOne(id);
    	List<Orders> order=orderlist.getOrderlist();
    	model.addAttribute("order", order);
    	model.addAttribute("orderlist", orderlist);
    	orderlist.setConfirmed(1);
		return "vieworders";
    	}
	
	@RequestMapping(value = "/deliver/{id}")
	public String deliver(Model model,@PathVariable int id,OrderList orderlist) {
		orderlist=repo.findOne(id);
		orderlist.setConfirmed(1);
		repo.save(orderlist);
		return "redirect:/admin/list";
	}
	@RequestMapping(value = "/farmers")
	public String farmers(Model model) {
		List<Users> farmers=repo1.findByRole("ROLE_FARMER");
		System.out.println(farmers);
		model.addAttribute("farmerslist", farmers);
		return "farmers";
	}
	@RequestMapping(value = "/customer")
	public String customers(Model model) {
		List<Users> farmers=repo1.findTop10ByOrderByShoppingTimesDesc();
		System.out.println(farmers);
		model.addAttribute("customer", farmers);
		return "topCustomer";
	}
	@RequestMapping(value = "/topproduct")
	public String topproduct(Model model) {
		List<Flower> farmers=flo.findTop10ByOrderByBoughtTimesDesc();
		System.out.println(farmers);
		model.addAttribute("topflowers", farmers);
		return "topFlower";
	}
	@RequestMapping(value = "/monthlysales")
	public String monthlysales(Model model) {
		//Sales of Jan
		
		List<Monthly> mon=new ArrayList<>();
		
		for(int i=0;i<12;i++) {
			List<OrderList> monthly=ord.findByMonth(i);
			double sales=0;
			for(OrderList of:monthly) {
				sales+=of.getAmount();
				}
			Monthly l=new Monthly();
			l.setMonth(i);
			l.setSales(sales);
			mon.add(l);	
			
		}
		System.out.println(mon);
		model.addAttribute("monthlysales", mon);
		return "month";
		}
	
}

