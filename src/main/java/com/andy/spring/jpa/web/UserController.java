package com.andy.spring.jpa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.andy.spring.jpa.entity.User;
import com.andy.spring.jpa.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import java.security.MessageDigest;
import java.util.List;

@Controller
public class UserController {

    @Resource
    UserService userService;
    @Autowired
    private MessageDigest md;

    @RequestMapping("/")
    public String index() {
        return "redirect:/list";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        List<User> users=userService.getUserList();
        model.addAttribute("users", users);
        return "user/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/userAdd";
    }

    @RequestMapping("/add")
    public String add(User user) {
        userService.save(user);
        return "redirect:/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit(Model model,Long id) {
        User user=userService.findUserById(id);
        model.addAttribute("user", user);
        return "user/userEdit";
    }

    @RequestMapping("/edit")
    public String edit(User user) {
        userService.edit(user);
        return "redirect:/list";
    }


    @RequestMapping("/delete")
    public String delete(Long id) {
        userService.delete(id);
        return "redirect:/list";
    }
    
    @RequestMapping("/toFindName")
    public String toFindByName()
    {
    	return "user/userFindName";
    }
    
    @RequestMapping("/findName")
    public String findByName(Model model, String userName, HttpServletResponse res)
    {
    	User user = userService.findByName(userName);
    	if (user!=null)
    	{
    		model.addAttribute("user", user);
    		return "user/user";
    	}
    	else 
    	{
    		return "redirect:/error/User_Not_Found";
    	}
    	
    }
    
    @RequestMapping("/error/{error}")
    @ResponseBody
    public String showError(@PathParam("error") String error) 
    {
    	return error;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET )
    public String toLogin()
    {	
    	return "user/login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model,String userName, String password, RedirectAttributes ra)
    {	
    	String result="";
    	User user = userService.findByName(userName);
    	if (user != null)
    	{
    		if (user.getPassword().equals(toHash(password)))
    		{
    			ra.addFlashAttribute("user", user);
    			return "redirect:/user";
    		}
    		else
    		{
    			model.addAttribute("message", "Password is wrong");
    			return "user/login";
    		}
    	}
    	else
    	{
    		model.addAttribute("message", "Username is wrong");
			return "user/login";
    	}
    	
    	
    }
    
    @RequestMapping(value = "/user")
    public String showUser(Model model, User user) 
    {
    	model.addAttribute("user", user);
    	return "user/user";
    }
    
    private String toHash(String str)
    {
    	md.update(str.getBytes());
        
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
    	return sb.toString();
    }
}
