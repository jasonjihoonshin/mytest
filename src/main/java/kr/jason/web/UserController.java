package kr.jason.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.jason.domain.User;
import kr.jason.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	//private List<User> users = new ArrayList<User>();
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginForm(){
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session){
		User user = userRepository.findByUserID(userId);
		if(user == null){
			System.out.println("Login failure!!");
			return "redirect:/users/loginForm";
		}
		if(!password.equals(user.getPassword())){
			System.out.println("Login failure!!");
			return "redirect:/users/loginForm";
		}
		System.out.println("Login success!!");
		session.setAttribute("user", user);
		return "redirect:/";
	}
	
	@GetMapping("/form")
	public String form(){
		return "/user/form";
	}
	@PostMapping("")
	public String create(User user, Model model){
		System.out.println("user= "+user);
		//users.add(user);
		userRepository.save(user);
		//model.addAttribute("userID", user.userID);
		//model.addAttribute("name", user.name);
		return "redirect:/users";
	}
	
	//@RequestMapping(value="/users", method = RequestMethod.GET)			
	@GetMapping("")
	public String list(Model model){
		//model.addAttribute("users", users);
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model){
		System.out.println(id);
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	//@PostMapping("{id}")
	@PutMapping("{id}")
	public String update(@PathVariable Long id, User newUser){
		User user = userRepository.findOne(id);
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
