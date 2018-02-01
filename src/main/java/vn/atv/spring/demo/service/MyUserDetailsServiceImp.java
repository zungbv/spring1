package vn.atv.spring.demo.service;

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

        UserBuilder builder=null;
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
}
