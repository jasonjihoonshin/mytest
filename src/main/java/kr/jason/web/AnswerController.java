package kr.jason.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.jason.domain.Answer;
import kr.jason.domain.AnswerRepository;
import kr.jason.domain.Question;
import kr.jason.domain.QuestionRepository;
import kr.jason.domain.User;

@Controller
@RequestMapping("questions/{questionId}/answers")
public class AnswerController {
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session ){
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(loginUser, question, contents);
		answerRepository.save(answer);	
		return String.format("redirect:/questions/%d", questionId);
	}
	

}
