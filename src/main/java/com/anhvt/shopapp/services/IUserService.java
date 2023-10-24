package com.anhvt.shopapp.services;

import com.anhvt.shopapp.dtos.UpdateUserDTO;
import com.anhvt.shopapp.dtos.UserDTO;
import com.anhvt.shopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Long roleId) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;
}
