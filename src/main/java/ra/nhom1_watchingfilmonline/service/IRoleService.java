package ra.nhom1_watchingfilmonline.service;


import ra.nhom1_watchingfilmonline.model.entity.Roles;

public interface IRoleService {
    Roles findRolesByRoleName(String roleName);

}
