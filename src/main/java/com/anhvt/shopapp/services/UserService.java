package com.anhvt.shopapp.services;

import com.anhvt.shopapp.dtos.UpdateUserDTO;
import com.anhvt.shopapp.dtos.UserDTO;
import com.anhvt.shopapp.exceptions.DataNotFoundException;
import com.anhvt.shopapp.models.Role;
import com.anhvt.shopapp.models.User;
import com.anhvt.shopapp.repositories.RoleRepository;
import com.anhvt.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        // kiem tra sdt ton tai
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        // convert userDTO -> user
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .active(true)
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found!"));
        user.setRole(role);
        // kiem tra neu co account id, khong yeu ca password
        if(userDTO.getGoogleAccountId() == 0 && userDTO.getFacebookAccountId() ==0){
            String password = userDTO.getPassword();
//            String encodedPassword =  passwordEncoded.encode(password);
//            user.setPassword(encodedPassword);
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password, Long roleId) throws Exception {
        // spring security
        return null;
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        return null;
    }

    @Override
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        return null;
    }
}
