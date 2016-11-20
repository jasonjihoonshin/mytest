package kr.jason.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question {
	@Id
	@GeneratedValue
	@JsonProperty
	private Long id;
	
	//private String writer;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_key"))
	@JsonProperty
	private User writer;
	
	@JsonProperty
	private String title;
	
	@Lob
	@JsonProperty
	private String contents;
	
	@JsonProperty
	private Integer countOfAnswer = 0;
	
	private LocalDateTime createTime;
	
	//Answer.java에서 정의 되어있는 Question 변수와 Mapping
	@OneToMany(mappedBy = "question")
	@OrderBy("id DESC")
	private List<Answer> answers;
	
	public Question(){};
	
	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createTime = LocalDateTime.now();
	}
	public String getFormattedCreateDate(){
		if(createTime == null){
			return "";
		}
		return createTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));		
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public boolean isSameUser(User loginUser) {
		return this.writer.equals(loginUser);
	}

	public void addAnswer() {
		// TODO Auto-generated method stub
		this.countOfAnswer += 1;
	}
	public void deleteAnswer(){
		this.countOfAnswer -=1;
	}
	
	
}
