package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.Parent;

import java.util.List;

public interface ParentService {



    Parent getParentById(Long id);
    List<Parent> getAllParents();
    Parent updateParent(Long id, Parent parent);
    void deleteParent(Long id);

    String add(Parent parent, String roleName);
}
