package ra.nhom1_watchingfilmonline.service;


import ra.nhom1_watchingfilmonline.model.entity.Roles;

import java.util.List;

public interface IRoleService {
    Roles findRolesByRoleName(String roleName);
    public List<Roles> findAllRole();

}
