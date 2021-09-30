package tackle.easay.com.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="ams_user_role_menu")
@Data
public class UserRoleMenu implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="menu_id")
	private Menu MenuId;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userId;
	@JoinColumn(name="role_id")
	@ManyToOne
	private Role roleId;
	private Long companyId;
	@Column(name="display_order")
	private Integer displayOrder;
}
