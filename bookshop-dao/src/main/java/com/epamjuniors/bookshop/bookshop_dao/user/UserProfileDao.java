package com.epamjuniors.bookshop.bookshop_dao.user;

import java.util.List;

import com.epamjuniors.bookshop.bookshop_model.user.UserProfile;
 
 
public interface UserProfileDao {
 
    List<UserProfile> findAll();
     
    UserProfile findByType(String type);
     
    UserProfile findById(int id);
}