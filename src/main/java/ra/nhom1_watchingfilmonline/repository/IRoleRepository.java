package ra.nhom1_watchingfilmonline.repository;


import ra.nhom1_watchingfilmonline.model.entity.Roles;

public interface IRoleRepository {
    Roles findRolesByRoleName(String roleName);
}

