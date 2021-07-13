package net.opentrends.vue.simulator.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Document(collection = "user")
public class User {
	
	@Id
	private String id;
	
	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	private String email;
	private String fullName;
	private String password;
	private boolean enabled;
	
	@DBRef
	private Set<Role> roles;
	
}
