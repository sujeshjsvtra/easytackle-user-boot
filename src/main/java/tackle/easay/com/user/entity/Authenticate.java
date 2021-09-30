package tackle.easay.com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ams_authenticate")
@Getter @Setter @AllArgsConstructor
@Builder
public class Authenticate extends Audit{

	public Authenticate() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User userId;

	private String username;
	private String password;
	@Column(name="secret_code")
	private Integer secretCode; 
	@Column(name="expire_in")
	private Integer expireIn;
	private Integer status;
	private String salt;
}
