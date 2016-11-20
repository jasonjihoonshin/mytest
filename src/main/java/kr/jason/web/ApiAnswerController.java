package kr.jason.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.jason.domain.Answer;
import kr.jason.domain.AnswerRepository;
import kr.jason.domain.Question;
import kr.jason.domain.QuestionRepository;
import kr.jason.domain.Result;
import kr.jason.domain.User;

//@Controller
// Json : @Controller > @RestController
@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@PostMapping("")
	//String > Answer (for Json)
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session ){
		if(!HttpSessionUtils.isLoginUser(session)){
			//return "/users/loginForm";
			return null;
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(loginUser, question, contents);
		//answerRepository.save(answer);	
		question.addAnswer();
		return answerRepository.save(answer);	
		//return String.format("redirect:/questions/%d", questionId);
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)){
			return Result.fail("로그인하시기 바랍니다.");
		}
		Answer answer = answerRepository.findOne(id);
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!answer.isSameWriter(loginUser)){
			return Result.fail("자신의 글만 삭제할 수 있습니다.");
		}
		
		answerRepository.delete(id);
		
		Question question= questionRepository.findOne(questionId);
		question.deleteAnswer();
		questionRepository.save(question);
		
		return Result.ok();
	}
	

}
