package com.example.demo.repository;

import com.example.demo.model.Roles;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.roles FROM User u WHERE u.username = :username AND u.password = :password")
    List<Roles> findRolesByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
