package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.Admin;
import com.djo.school_pfe.entity.Role;
import com.djo.school_pfe.entity.Validation;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.AdminRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.service.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    @Autowired
    Validation validation;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public String add(Admin admin, String roleName) {


        if (this.adminRepository.existsByUserNameOrEmailAddress(admin.getUserName(), admin.getEmailAddress())) {
            throw new BadRequestException("Username or email-address already used");
        }

        Role role = this.roleRepository.findByRoleName(roleName);
        admin.setRoles(Collections.singletonList(role));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        this.adminRepository.save(admin);
        return "Admin saved successfully";
    }



    @Override
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin updateAdmin(Long id, Admin admin) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            // Update the admin's details
            admin.setUserId(id); // Set the ID of the admin to be updated
            return adminRepository.save(admin);
        } else {
            throw new IllegalArgumentException("Admin with id " + id + " does not exist!");
        }
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
