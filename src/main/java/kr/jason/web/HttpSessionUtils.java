package kr.jason.web;

import javax.servlet.http.HttpSession;

import kr.jason.domain.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "sessionedUser";
	public static boolean isLoginUser(HttpSession session){
		Object sessonedUser = session.getAttribute(USER_SESSION_KEY);
		if(sessonedUser == null){
			return false;
		}
		return true;
	}
	public static User getUserFromSession(HttpSession session){
		if(!isLoginUser(session)){
			return null;
		}
		return (User)session.getAttribute(USER_SESSION_KEY);
	}
}
