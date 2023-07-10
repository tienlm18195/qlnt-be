package com.tainika.qlnt.qlnt.service;

import com.tainika.qlnt.qlnt.model.Authority;
import com.tainika.qlnt.qlnt.model.Role;
import com.tainika.qlnt.qlnt.repository.AuthorityRepository;
import com.tainika.qlnt.qlnt.repository.RoleRepository;
import com.tainika.qlnt.qlnt.constants.AppUserRole;
import com.tainika.qlnt.qlnt.constants.Message;
import com.tainika.qlnt.qlnt.constants.AppUserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tainika.qlnt.qlnt.constants.AppUserRole.GUEST;
import static com.tainika.qlnt.qlnt.ultil.DateUtils.now;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public Role findByRoleCode(AppUserRole role) {
        return roleRepository.findByRoleCode(role.getCode());
    }

    public MessageResultService<?> create(AppUserRole role) {
        try {
            String c = Objects.nonNull(role) ? role.getCode() : GUEST.getCode();

            List<Authority> authorities = AppUserRole.valueOf(c).getPermissions()
                .stream()
                .map(AppUserPermission::getPermission)
                .map(Authority::new)
                .collect(Collectors.toList());
            List<Authority> lsAuth = authorityRepository.saveAll(authorities);

            if (lsAuth.isEmpty()) {
               return MessageResultService.builder()
                .action(Message.ACTION.CREATE)
                .content("List auth can't be created")
                .build().withFailureResponse();
            }

            Role r = new Role();
            r.setCode(c);
            r.setAuthorities(lsAuth);
            r.setCreateTime(now());
            r.setUpdateTime(now());
            roleRepository.save(r);
            return new MessageResultService<>(Message.ACTION.CREATE, r).withSuccessResponse();
        } catch (Exception ex) {
            return new MessageResultService<>(Message.ACTION.CREATE, ex.getMessage(), null).withErrorResponse();
        }
    }
}
