package tackle.easay.com.user.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ams_user")
@Getter @Setter @NoArgsConstructor  @AllArgsConstructor
@Generated
@Builder
public class User  implements Serializable{

	public User(Long userId) {
		this.id=userId;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	@Column(name="contact_number")
	private String contactNumber;
	private String thumbnail;
	@Column(name="referal_code")
	private String referalCode; 
	@Column(name="company_id")
	private Long companyId;
	private Integer status;
	@OneToMany(fetch = FetchType.LAZY,mappedBy ="roleId" )
	List<UserRole> roles;
	
}
