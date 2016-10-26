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
import kr.jason.domain.Result;
import kr.jason.domain.User;


@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/user/login";
		}
		return "/qna/form";
	}
	@PostMapping("")
	public String create(String title, String contents, HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/user/login";
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
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);
		if(!result.isValid()){
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";			
		}
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}
	
	private Result valid(HttpSession session, Question question){
		if(!HttpSessionUtils.isLoginUser(session)){
			return Result.fail("로그인이 필요합니다.");
		}
		User logindUser = HttpSessionUtils.getUserFromSession(session);
		if(!question.isSameUser(logindUser)){
			return Result.fail("작성자만 수정/삭제 가능합니다.");
		}
		return Result.ok();
	}
	
	private boolean hasPermission(HttpSession session, Question question){
		if(!HttpSessionUtils.isLoginUser(session)){
			throw new IllegalStateException("로그인이 필요합니다.");
		}
		User logindUser = HttpSessionUtils.getUserFromSession(session);
		if(!question.isSameUser(logindUser)){
			throw new IllegalStateException("작성자만 수정/삭제 가능합니다.");
		}
		return true;
	}
	//@RequestMapping(value="/questions", method=RequestMethod.PUT)
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, Model model, String title, String contents, HttpSession session){
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);
		if(!result.isValid()){
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";			
		}
		question.update(title,contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d",id);
	
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session){
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);
		if(!result.isValid()){
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";			
		}
		questionRepository.delete(id);
		return "redirect:/";
	}
	
}
