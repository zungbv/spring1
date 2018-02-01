package vn.atv.spring.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity

public class Role {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int role_id;
	private String role_name;
	
	@ManyToMany(mappedBy="roles")
	private List<User> users=new ArrayList<User>(10);
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
