package com.freeagents;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeagents.model.Address;

@Controller
public class AddressController {

	@RequestMapping(value="/address", method=RequestMethod.GET)
	public String prepareNewAddress(Model model){
		model.addAttribute("adresche", new Address());
		return "address";
	}

	@RequestMapping(value="/address", method=RequestMethod.POST)
	public String addReadyAddress(@ModelAttribute Address addr, Model model){
		System.out.println(addr);
		//add to DAO
		model.addAttribute("greeting", "Address created successfully");
		model.addAttribute("address", addr);
		return "hello";
	}
	
}
