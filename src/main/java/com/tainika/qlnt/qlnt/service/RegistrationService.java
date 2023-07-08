package com.tainika.qlnt.qlnt.service;

import com.tainika.qlnt.qlnt.constants.AppUserRole;
import com.tainika.qlnt.qlnt.dto.signup.NewUserRequest;
import com.tainika.qlnt.qlnt.model.Role;
import com.tainika.qlnt.qlnt.model.User;
import com.tainika.qlnt.qlnt.repository.UserRepository;
import com.tainika.qlnt.qlnt.constants.Message;
import com.tainika.qlnt.qlnt.constants.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleService roleService;

    public User createNewUser(User newUser) {
        return userRepository.save(newUser);
    }

    public MessageResultService<?> signUp(NewUserRequest request) {
        try {
            boolean isExistedUser = userRepository.isExistedUserName(request.getUserName());
            if (isExistedUser) {
                return new MessageResultService<>(Message.ACTION.SIGN_UP, Message.ALERT.USER_EXISTED, null)
                    .withFailureResponse();
            }

            boolean isExistedEmail = userRepository.isExistedEmail(request.getEmail());
            if (isExistedEmail) {
                return new MessageResultService<>(Message.ACTION.SIGN_UP, Message.ALERT.EMAIL_EXISTED, null)
                    .withFailureResponse();
            }

            Role role = roleService.findByRoleCode(AppUserRole.GUEST.getCode());
            if (role == null) {
                MessageResultService<?> newRole = roleService.create(AppUserRole.GUEST.getCode());
                if (newRole.getStatus().equals(Status.COMMON.ERROR)) {
                    return new MessageResultService<>(Message.ACTION.SIGN_UP, newRole.getResponseMessage() ,null)
                        .withFailureResponse();
                }

                role = (Role) newRole.getItem();
            }

            User newUser = request.convertToUser(encoder.encode(request.getPassword()));
            newUser.setRole(role);
            userRepository.save(newUser);
            return new MessageResultService<>(
                Message.ACTION.SIGN_UP,
                newUser.convertToNewUserResponseData()
            ).withSuccessResponse();
        } catch (Exception err) {
            return new MessageResultService<>(Message.ACTION.SIGN_UP, err.getMessage(), null)
                .withErrorResponse();
        }
    }
}
