package com.tainika.qlnt.qlnt.service;

import com.tainika.qlnt.qlnt.dto.signup.NewUserRequest;
import com.tainika.qlnt.qlnt.model.Role;
import com.tainika.qlnt.qlnt.model.User;
import com.tainika.qlnt.qlnt.repository.UserRepository;
import com.tainika.qlnt.qlnt.constants.Message;
import com.tainika.qlnt.qlnt.constants.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.tainika.qlnt.qlnt.constants.AppUserRole.ADMIN;
import static com.tainika.qlnt.qlnt.constants.AppUserRole.GUEST;

@Service
public class RegistrationService {

    private final BaseService baseService;

    private final PasswordEncoder encoder;

    private final RoleService roleService;

    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(BaseService baseService,
                               PasswordEncoder encoder,
                               RoleService roleService,
                               UserRepository userRepository) {
        this.baseService = baseService;
        this.encoder = encoder;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

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

            boolean isAdmin = isAdmin(request.getUserName());
            Role role = baseService.getOrCreateRole(isAdmin ? ADMIN : GUEST);

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

    private boolean isAdmin(String admin) {
        return admin.equalsIgnoreCase("admin");
    }
}
