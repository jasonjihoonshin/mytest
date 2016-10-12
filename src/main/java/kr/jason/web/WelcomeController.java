package kr.jason.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	@GetMapping("/helloworld")
	public String welcome(Model model){
	//public String welcome(String name, int age, Model model){
		//System.out.println("name= "+name + " age= "+age);
		//model.addAttribute("name", name);
		//model.addAttribute("age", age);
		
		//model.addAttribute("name","Chris");
		//model.addAttribute("value",10000);
		//model.addAttribute("taxed_value",30);
		//model.addAttribute("in_ca",true);	
		
		List<MyModel> repo = Arrays.asList(new MyModel("test1"), new MyModel("test2"));
		model.addAttribute("repo", repo);
		
		return "welcome";
	}

}
