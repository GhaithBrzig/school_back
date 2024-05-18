package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.Parent;
import com.djo.school_pfe.entity.PhotoState;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ParentService {


    public void assignEnfantToParent(Long parentId, Long enfantId);
    Parent getParentById(Long id);
    List<Parent> getAllParents();
    Parent updateParent(Long id, Parent parent);
    void deleteParent(Long id);

    String add(Parent parent, String roleName);

    public void uploadPhoto(Long parentId, MultipartFile file) throws IOException;

}
