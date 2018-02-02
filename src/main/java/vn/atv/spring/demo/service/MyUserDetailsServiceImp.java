package vn.atv.spring.demo.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.transaction.annotation.Transactional;
import vn.atv.spring.demo.model.Role;
import vn.atv.spring.demo.model.User;
import vn.atv.spring.demo.repository.UserRepository;


import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MyUserDetailsServiceImp implements UserDetailsService, MyUserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException(username);
        }

        UserBuilder builder;
        builder=org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(user.getPassword());
        List<Role> roles=user.getRoles();

        String[] roleNameArray=new String[roles.size()];
        for(int i=0;i<roles.size();i++) {
            roleNameArray[i]="ROLE_"+roles.get(i).getRole_name();
        }
        builder.authorities(roleNameArray);

        return builder.build();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers(String adminUsername) {
        return userRepository.findAll(adminUsername);
    }

    @Override
    public void updateImage(String username, String imageFileName) {
        userRepository.updateImage(username,imageFileName);
    }

    @Override
    public void updateInfo(User user) {
        userRepository.updateInfo(user.getAddress(),user.getFirstName(),user.getLastName(),user.getPhone(),user.getUsername());
    }

    @Override
    public boolean updateEmail(String newEmail, String confirmPassword, User user) {
        if(user.getPassword().equals(confirmPassword)){
            userRepository.updateEmail(newEmail,user.getUsername());
            return true;
        }else{
            return false;
        }


    }

    @Override
    public boolean updateEmailByAdmin(String newEmail, String confirmPassword, User admin, String username) {
        if(admin.getPassword().equals(confirmPassword)){
            userRepository.updateEmail(newEmail,username);
            return true;
        }else{
            return false;
        }
    }



    @Override
    public boolean deleteUser(String username) {
        try{
            userRepository.deleteByUsername(username);
            return true;
        }catch (Exception e){
            return false;
        }


    }

    @Override
    public Workbook createUserListWorkBook(Map<String, Object> map) {
        List<User> users=(List<User>)map.get("users");
        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("all users");

        Row header=sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("First Name");
        header.createCell(2).setCellValue("Last Name");
        header.createCell(3).setCellValue("Address");
        header.createCell(4).setCellValue("Email");
        header.createCell(5).setCellValue("Phone");
        header.createCell(6).setCellValue("Username");

        int rowCount=1;
        Row userRow;
        for(User user:users){
            userRow=sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(user.getUser_id());
            userRow.createCell(1).setCellValue(user.getFirstName());
            userRow.createCell(2).setCellValue(user.getLastName());
            userRow.createCell(3).setCellValue(user.getAddress());
            userRow.createCell(4).setCellValue(user.getEmail());
            userRow.createCell(5).setCellValue(user.getPhone());
            userRow.createCell(6).setCellValue(user.getUsername());
        }
        return workbook;
    }
}
