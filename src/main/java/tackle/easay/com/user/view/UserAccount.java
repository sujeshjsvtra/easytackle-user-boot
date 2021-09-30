package tackle.easay.com.user.view;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import tackle.easay.com.user.entity.Authenticate;
import tackle.easay.com.user.entity.User;

@Getter
@Setter
public class UserAccount implements Serializable{

	private Long id;
	private String name;
	private String email;
	private String contactNumber;
	private String thumbnail;
	private Integer secretCode;
	private String organization;
	private String referalCode;
	private String password;
	
	 
	public User getUser() {
		return User.builder()
					.name(name)
					.contactNumber(contactNumber)
					.email(email)
					.referalCode(referalCode)
					.build();
				
	}
	
	public Authenticate getAuthenticate() {
		return Authenticate.builder()
				.username(email)
				.password(password)
				.secretCode(secretCode)
				.build();
	}
	
	 
}
