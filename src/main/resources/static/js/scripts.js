$(".answer-write input[type = submit]").click(addAnswer);
function addAnswer(e){
	// 서버에 데이터를 보내지 않기 위해 
	e.preventDefault();
	// 개발자 도구에 출력 
	console.log("click !!");
	
	var queryString = $(".answer-write").serialize();
	console.log("queryString : "+ queryString);
	
	var url = $(".answer-write").attr("action");
	console.log("url : "+url);
	
	$.ajax(
			{type : 'post',
			url : url,
			data : queryString,
			dataType : 'json',
			error : onError,
			success : onSuccess});
}
function onError(){
	
}
function onSuccess(data, status){
	console.log(data);
	// id : #, class : . x 을 사용함
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(data.writer.userID, data.formattedCreateDate, data.contents, data.id, data.question.id, data.id);
										// 0                    1                        2              3         4                 5    
	$(".qna-comment-slipp-articles").prepend(template);
	
	$("textarea[name=contents]").val("");
}

String.prototype.format = function() {
	  var args = arguments;
	  return this.replace(/{(\d+)}/g, function(match, number) {
	    return typeof args[number] != 'undefined'
	        ? args[number]
	        : match
	        ;
	  });
	};

$("a.link-delete-article").click(deleteAnswer);
function deleteAnswer(e){
	e.preventDefault();
	
	var deleteBtn = $(this); // javascript에서 this는 계속 바뀌므로 변수로 정의하여 사용
	var url = deleteBtn.attr("href");
	console.log("url : "+url);
	
	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function(xhr, status){
			console.log("error");
		},
		success : function(data, status){
			console.log(data);
			if(data.valid){
				deleteBtn.closest("article").remove();
			} else{
				alert(data.errorMessage);
			}
				
		}
	});
}

/*
$(document).ready(function(){/* jQuery toggle layout /
$('#btnToggle').click(function(){
  if ($(this).hasClass('on')) {
    $('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
    $(this).removeClass('on');
  }
  else {
    $('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
    $(this).addClass('on');
  }
});
});*/