package br.com.mfsdevsys.cursoms.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false, length =60)
	private String firstName;
	
	@Column(nullable=true, length =60)
	private String lastName;
	
	@Column(nullable=false, length =100)
	private String email;
	
	private Boolean status;
	
	@Column(nullable=false)
	private String password;
	
	@Column(name="created_at",columnDefinition ="TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;
	
	@Column(name="updated_at",columnDefinition ="TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="user_role",
	   joinColumns=@JoinColumn(name="user_id"),
	   inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}
	
	public User(Long id, String firstName, String lastName, String email, Boolean status, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.status = status;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}
	
	

	public Set<Role> getRoles() {
		return roles;
	}

	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		updatedAt = Instant.now();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}

}

