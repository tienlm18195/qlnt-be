package com.tainika.qlnt.qlnt.service;

import com.google.common.base.Strings;
import com.tainika.qlnt.qlnt.model.Authority;
import com.tainika.qlnt.qlnt.model.Role;
import com.tainika.qlnt.qlnt.repository.AuthorityRepository;
import com.tainika.qlnt.qlnt.repository.RoleRepository;
import com.tainika.qlnt.qlnt.constants.AppUserRole;
import com.tainika.qlnt.qlnt.constants.Message;
import com.tainika.qlnt.qlnt.constants.AppUserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.tainika.qlnt.qlnt.ultil.DateUtils.now;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public Role findByRoleCode(String code) {
        Role r = roleRepository.findByRoleCode(Strings.nullToEmpty(code));
        if (r == null) {
            MessageResultService.builder()
                .action(Message.ACTION.SEARCH)
                .content(Message.ALERT.NO_RESULT)
            .build().withFailureResponse();
        }

        return r;
    }

    public MessageResultService<?> create(String code) {
        try {
            String c = Strings.isNullOrEmpty(code) ? AppUserRole.GUEST.getCode() : code;

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
