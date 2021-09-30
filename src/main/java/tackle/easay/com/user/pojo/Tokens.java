package tackle.easay.com.user.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data @Builder @AllArgsConstructor
public class Tokens implements Serializable {
	@NotNull
	private Long id;
	@NotNull
	private String clientId;
	@NotNull
	private String transporter;
	@NotNull
	private String token;
	@NotNull
	private String expireIn;
	
	 
}
