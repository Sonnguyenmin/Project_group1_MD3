package ra.nhom1_watchingfilmonline.repository;


import ra.nhom1_watchingfilmonline.model.entity.Roles;

import java.util.List;

public interface IRoleRepository {
    Roles findRolesByRoleName(String roleName);
    public List<Roles> findAll();
}

