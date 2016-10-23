package kr.jason.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.jason.domain.Question;
import kr.jason.domain.QuestionRepository;
import kr.jason.domain.User;


@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		return "/qna/form";
	}
	@PostMapping("")
	public String create(String title, String contents, HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		//Question newQuestion = new Question(sessionedUser.getUserID(),title, contents);
		Question newQuestion = new Question(sessionedUser,title, contents);
		questionRepository.save(newQuestion);
		
		return "redirect:/";
	}
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model){
		model.addAttribute("question", questionRepository.findOne(id));
		return "/qna/show";
	}
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session){
		//strengthen security
		//make sure login user
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		//make sure login user = writer
		User logindUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		if(!question.isSameUser(logindUser)){
			return "/users/loginForm";
		}
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}
	//@RequestMapping(value="/questions", method=RequestMethod.PUT)
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session){
		
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		//make sure login user = writer
		User logindUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		if(!question.isSameUser(logindUser)){
			return "/users/loginForm";
		}
		
		question.update(title,contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d",id);
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, HttpSession session){
		
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		//make sure login user = writer
		User logindUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		if(!question.isSameUser(logindUser)){
			return "/users/loginForm";
		}
		
		questionRepository.delete(id);
		return "redirect:/";
	}
	


}
