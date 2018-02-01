package vn.atv.spring.demo.service;

import vn.atv.spring.demo.model.User;

import java.util.List;

public interface MyUserDetailsService {
    public User getUserByUsername(String username);
    public List<User> getAllUsers(String adminUsername);
    public void updateImage(String username, String imageFileName);
    public void updateInfo(User user);
    public boolean updateEmail(String newEmail, String confirmPassword, User user);
    public boolean updateEmailByAdmin(String newEmail, String confirmPassword, User admin, String username);

    public boolean deleteUser(String username);
}
