package vn.atv.spring.demo.controller;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import vn.atv.spring.demo.model.User;
import vn.atv.spring.demo.service.MyUserDetailsService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@Controller
@PropertySource("classpath:myDemoApp.properties")
public class UserController {
	@Value("${demo.pathToUserImage}")
	private static String pathToUserImage;


    @Autowired
    private Environment env;


	@Autowired
	private MyUserDetailsService myUserService;


	@GetMapping("/")
	public String home() {
		return "homePage";
	}
	@GetMapping("/login")
	public String getLoginForm() {
		return "login";
	}

	@GetMapping("/welcome")
	public String welcome(Model model, Principal principal, HttpSession session) {
				
		User user= myUserService.getUserByUsername(principal.getName());
		session.setAttribute("user", user);		
		model.addAttribute("user", user);
		return "welcome";
	}

	@GetMapping("/user")
	public String getUserHome(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		return "userHome";
	}
	@PostMapping("/user/updateInfo")
	public RedirectView updateUserInfo(@ModelAttribute("user") User user,HttpSession session, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {			
	    	System.out.println("binding error");
	    }else {
	    	user.setUsername(principal.getName());	    		    	
	    	myUserService.updateInfo(user);
	    }		
		return new RedirectView("../welcome");
	}
	@RequestMapping(value = "user/uploadFile", method = RequestMethod.POST)	
	public RedirectView updateUserImage(@RequestParam("file") MultipartFile file, Principal principal) {
		processFileUpload(file, principal.getName());
		return new RedirectView("../welcome");
	}
	private void processFileUpload(MultipartFile file, String username) {
		String fileName = file.getOriginalFilename().replaceAll("\\s+", "");		
		if(pathToUserImage==null){
		    pathToUserImage=env.getProperty("demo.pathToUserImage");
        }
		if (!file.isEmpty()) {
			File dir = new File(pathToUserImage);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String userImage = dir.getAbsolutePath() + File.separator + fileName;
			File serverFile = new File(userImage);

			try {
				file.transferTo(serverFile);				
				myUserService.updateImage(username, fileName);
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	
	@PostMapping("user/updateUserEmail")
	@ResponseBody
	public boolean updateUserEmail(@RequestParam("confirmPassword") String confirmPassword,@RequestParam("newEmail") String newEmail, HttpSession session) {
		
		return myUserService.updateEmail(newEmail, confirmPassword,(User)session.getAttribute("user"));
		
	}
	@GetMapping("admin/userList.xlsx")
	public @ResponseBody void getUserListInExcelFile(Principal principal, HttpSession session, HttpServletResponse response) throws IOException {
		List<User> users;
		if(session.getAttribute("users")==null){
			users = myUserService.getAllUsers(principal.getName());
		}else{
			users=(List<User>)session.getAttribute("users");
		}
        Map<String,Object> map=new HashMap<>();
		map.put("users",users);
        Workbook workbook=myUserService.createUserListWorkBook(map);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition","attachment;filename=userList.xlsx");
        workbook.write(response.getOutputStream());

	}
	
	
	@GetMapping("/admin")
	public String getAdminHome(Model model, Principal principal, @ModelAttribute("user") User user, HttpSession session) {
		List<User> users = myUserService.getAllUsers(principal.getName());
        session.setAttribute("users",users);
		model.addAttribute("users", users);
		model.addAttribute("username", principal.getName());
		return "adminHome";
	}
	@PostMapping("/admin/updateUserInfo")
	public RedirectView editUserInfo(@ModelAttribute("user") User user) {
		myUserService.updateInfo(user);
		
		return new RedirectView(".");
	}
	@PostMapping("/admin/updateUserEmail")
	@ResponseBody
	public boolean editUserEmail(Principal principal,@RequestParam("confirmPassword") String confirmPassword,@RequestParam("newEmail") String newEmail,@RequestParam("username") String username, HttpSession session) {
		User user=(User)session.getAttribute("user");
		if(user==null) {
			user=myUserService.getUserByUsername(principal.getName());
			session.setAttribute("user", user);
		}		
		return myUserService.updateEmailByAdmin(newEmail, confirmPassword,user,username);
		
	}	
	@PostMapping("admin/updateUserImage")	
	public RedirectView editUserImage(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
		processFileUpload(file, username);
		return new RedirectView(".");
	}
	@GetMapping("admin/deleteUser/{username}")
	@ResponseBody
	public boolean deleteUser(@PathVariable(name="username") String username) {
		return myUserService.deleteUser(username);
		
	}
	

}
