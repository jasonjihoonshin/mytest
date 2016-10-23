package kr.jason.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length=20, unique = true)
	public String userID;
	
	public String password;
	public String name;
	public String email;

	//public Long getId(){
	//	return id;
	//}
	
	public boolean matchId(Long newId){
		if(newId == null){
			return false;
		}
		return newId.equals(id);
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getUserID(){
		return userID;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	//public String getPassword(){
	//	return this.password;
	//}
	
	public boolean matchPassword(String newPassword){
		if(newPassword == null){
			return false;
		}
		return newPassword.equals(password);
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}

	public void update(User newUser) {
		this.password = newUser.password;
		this.name = newUser.name;
		this.email = newUser.email;	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}
