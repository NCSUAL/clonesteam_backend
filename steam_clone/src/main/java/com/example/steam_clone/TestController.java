package com.example.steam_clone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
	
	@GetMapping("/")
	public String index() {
		return "Hello java spring boot!";
	}

}
