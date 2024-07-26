package com.example.demo.service;
import com.example.demo.model.Roles;
import com.example.demo.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    public Roles findRoleByName(String name) {
        return rolesRepository.findRolesByName(name);
    }

    public Roles saveRole(Roles role) {
        return rolesRepository.save(role);
    }

    public Optional<Roles> findRoleById(Long id) {
        return rolesRepository.findById(id);
    }

    public void deleteRoleById(Long id) {
        rolesRepository.deleteById(id);
    }
}
