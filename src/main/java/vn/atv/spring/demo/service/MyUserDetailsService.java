package vn.atv.spring.demo.service;

import org.apache.poi.ss.usermodel.Workbook;
import vn.atv.spring.demo.model.User;

import java.util.List;
import java.util.Map;

public interface MyUserDetailsService {
    User getUserByUsername(String username);
    List<User> getAllUsers(String adminUsername);
    void updateImage(String username, String imageFileName);
    void updateInfo(User user);
    boolean updateEmail(String newEmail, String confirmPassword, User user);
    boolean updateEmailByAdmin(String newEmail, String confirmPassword, User admin, String username);

    boolean deleteUser(String username);
    Workbook createUserListWorkBook(Map<String, Object> map);
}
