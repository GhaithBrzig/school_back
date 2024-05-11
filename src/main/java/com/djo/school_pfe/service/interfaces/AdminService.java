package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.Admin;

import java.util.List;

public interface AdminService {



    Admin getAdminById(Long id);
    List<Admin> getAllAdmins();
    Admin updateAdmin(Long id, Admin admin);
    void deleteAdmin(Long id);

    String add(Admin admin, String roleName);
}
