package com.andy.spring.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andy.spring.jpa.entity.User;
import com.andy.spring.jpa.repository.UserRepository;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MessageDigest md;


    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
    	
        
        user.setPassword(toHash(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void edit(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
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
    
    @Override
    public User findByName(String name)
    {
    	User user = null;
    	List<User> users = userRepository.findAll();
    	
    	for(User mUser : users)
    	{
    		if (mUser.getUserName().equalsIgnoreCase(name))
    			user = mUser;
    	}
    	return user;
    }
	

	
}
