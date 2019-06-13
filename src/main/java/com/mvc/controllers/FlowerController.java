package com.mvc.controllers;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mvc.data.Flower;
import com.mvc.data.FlowerRepository;
import com.mvc.data.Item;
import com.mvc.data.OrderList;
import com.mvc.data.OrderListRepository;
import com.mvc.data.Orders;
import com.mvc.data.UserRepository;
import com.mvc.data.Users;

@Controller
@RequestMapping("/flower")
@SessionAttributes({"cart","cartsize","user"})
public class FlowerController {
	
	@Autowired
	FlowerRepository repo;
	@Autowired
	OrderListRepository repo2;
	@Autowired
	UserRepository user1;
	
	@ModelAttribute(value="flower")
	public Flower getFlower() {
		return new Flower();			
	}
	
	@RequestMapping(value = "/farmer/add", method=RequestMethod.GET)
	public String addFlower() {
		return "addflower";
	}

	@RequestMapping(value = "/farmer/add", method=RequestMethod.POST)
	public String saveFlower(@ModelAttribute Flower flower ) throws Exception {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		MultipartFile file=flower.getFile();
	    byte[] ibyte=file.getBytes();	
	    Blob blob=new SerialBlob(ibyte);
	    flower.setImage(blob);
		flower.setFarmersName(username);
		repo.save(flower);		
		return "addflower";
	}
	
	
	@RequestMapping(value="/farmer/products")
	public String products(Model model,Pageable pageable) throws Exception {
		String farmersName=SecurityContextHolder.getContext().getAuthentication().getName();
		List<Flower> flowers=repo.findByFarmersName(farmersName);
		for (Flower flower : flowers) {
			Blob image=flower.getImage();
			int bloblength=(int) image.length();
			byte[] b=image.getBytes(1, bloblength);
			String baseImage =Base64.getEncoder().encodeToString(b);
			flower.setBase64image(baseImage);	
			
		}
		model.addAttribute("flowerlist", flowers);
		return "product-list";	
	}
	
	@RequestMapping(value="/farmer/delete")
	public String remove(@RequestParam int id) {
		Flower flower=repo.findOne(id);
		flower.setDeleted(1);	
		repo.save(flower);
		return "redirect:/flower/farmer/products";
	}
	@RequestMapping(value="/farmer/edit")
	public String edit(@RequestParam int id,@ModelAttribute Flower flower) {
		ModelAndView nav=new ModelAndView("edit-flower");
		flower=repo.findOne(id);
		nav.addObject("flower", flower);
		return "redirect:/flower/farmer/products";
	} 
	
	@RequestMapping(value="/customer/find")
	public String findFlowers(Model model) throws Exception {
		List<Flower> flowers=repo.findAll();
		List<Flower> flowers1=repo.findByOrderByPriceDesc();
		List<Flower> flowers2=repo.findByOrderByPriceAsc();
		for(Flower f:flowers2) {
			System.out.println(f);
		}
		for (Flower flower : flowers) {
			Blob image=flower.getImage();
			int bloblength=(int) image.length();
			
			byte[] b=image.getBytes(1, bloblength);
			
			String baseImage =Base64.getEncoder().encodeToString(b);
			flower.setBase64image(baseImage);
			
		}
		model.addAttribute("flowerlist", flowers);
		model.addAttribute("flowerlist1", flowers1);
		model.addAttribute("flowerlist2", flowers2);
		return "flowers";	
	}
	
	@RequestMapping("/customer/{flowerId}")
	public String viewFlower(@PathVariable("flowerId") int flowerId, Model model) throws Exception {
		Flower flower=repo.findOne(flowerId);
		Blob image=flower.getImage();
		int bloblength=(int) image.length();

		byte[] b=image.getBytes(1, bloblength);
		
		String baseImage =Base64.getEncoder().encodeToString(b);
		flower.setBase64image(baseImage);
		model.addAttribute("flower", flower);
		return "flower";
	}
	
	@RequestMapping(value="/customer/buy/{id}", method=RequestMethod.POST)
	public String buy(@PathVariable int id,Model model,@ModelAttribute Item item,HttpServletRequest request) {
		HttpSession session = request.getSession();
		int itemquantity=item.getItemquantity();
		if(session.getAttribute("cart") == null) {
			
			List<Item> cart = new ArrayList<Item>();
			int i=cart.size();
			System.out.println("cartsize"+i);
			model.addAttribute("cartsize", i);
			cart.add(new Item(repo.findOne(id), itemquantity));
	
			model.addAttribute("cart", cart);
		}else {
			@SuppressWarnings("unchecked")
			List<Item> cart =(List<Item>)session.getAttribute("cart");
			int index=isExisting(id, cart);
			if (index==-1) {
				cart.add(new Item(repo.findOne(id), itemquantity));
			} else {
				cart.get(index).setItemquantity(itemquantity);

			}
			int cartsize=cart.size();
					
			System.out.println();
			model.addAttribute("cartsize", cartsize);
			model.addAttribute("cart", cart);
			
		}
		
		

		return "cart";
	}

	private int isExisting(int id,List<Item> cart) {
		for (int i = 0; i < cart.size()	; i++) {
			if (cart.get(i).getFlower().getFlowerId()==id) {
				return i;
			}

		}
		return -1;
	}
	@RequestMapping(value="/customer/cart")
	public  String cart() {
		
		return "cart";
	}
     @RequestMapping(value="/customer/order")
	public String orders(HttpSession session,Model model) {
		String customerName=SecurityContextHolder.getContext().getAuthentication().getName();
		Users user=user1.findByUsername(customerName);
		List<Orders> orders=new ArrayList<>();
		double amount = 0;
		int sold=0;
		@SuppressWarnings("unchecked")
		List<Item> cart=(List<Item>)session.getAttribute("cart");
		for(Item order:cart) {
			int itemQuantity=order.getItemquantity();
			Orders od=new Orders();
			od.setFlowerName(order.getFlower().getName());
			od.setItemquantity(itemQuantity);
			od.setFarmersName(order.getFlower().getFarmersName());
			double total=order.getFlower().getPrice()*itemQuantity;
			od.setSubprice(total);
			amount+=total;			
			int id=order.getFlower().getFlowerId();
			Flower flower=repo.findOne(id);
			sold=flower.getSold()+itemQuantity;
			flower.setSold(sold);
			int newQuantity=(flower.getQuantity())-itemQuantity;
			flower.setQuantity(newQuantity);
			repo.save(flower);
			orders.add(od);
		}
		OrderList list=new OrderList();
		list.setOrderlist(orders);
		list.setEmail(user.getEmail());
		list.setAddress(user.getAddress());
		list.setAmount(amount);
		model.addAttribute("amount", amount);
		list.setCustomerName(customerName);
		list.setOrderDate(new Date());
		repo2.save(list);
		cart.clear();
		return "redirect:/";
	}
     @RequestMapping(value="/customer/yourorders")
     public String yourorders(Model model) {
    	 String customerName=SecurityContextHolder.getContext().getAuthentication().getName();
    	 List<OrderList> customerorders=repo2.findByCustomerName(customerName);
    	 for(OrderList c:customerorders) {
    		 List<Orders> customer=c.getOrderlist();
    		 System.out.println(customer);
             
    	 }    	
    	 model.addAttribute("customer", customerorders);
	  return "your-orders";
    }
     @RequestMapping(value="/farmer/view/{id}")
     public String viewOrders(Model model,@PathVariable int id,OrderList orderlist) {
    	orderlist=repo2.findOne(id);
    	List<Orders> viewlist=orderlist.getOrderlist();
    	System.out.println(viewlist);
    	model.addAttribute("viewlist", viewlist);
		return "vieworders"; 
     }
     @RequestMapping(value="/customer/clear")
     public String removecart(HttpSession session) {
    	 @SuppressWarnings("unchecked")
		List<Item> cart =(List<Item>)session.getAttribute("cart");
    	 cart.clear();
    	 return "cart";
     }
 

}