package com.topBalance.wishTree.service;
import com.topBalance.wishTree.dto.User;
import com.topBalance.wishTree.mapper.UserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public List<Map<String, Object>> getAllUsers() {
        List<User> userList = userProfileMapper.getAllUsers();
        return userList.stream().map(user -> {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", user.getUserId());
            userMap.put("username", user.getUserName());
            return userMap;
        }).collect(Collectors.toList());
    }
}