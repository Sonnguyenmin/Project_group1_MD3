package ra.nhom1_watchingfilmonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.entity.Roles;
import ra.nhom1_watchingfilmonline.repository.IRoleRepository;
import ra.nhom1_watchingfilmonline.service.IRoleService;
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Roles findRolesByRoleName(String roleName) {
        return roleRepository.findRolesByRoleName(roleName);
    }
}
