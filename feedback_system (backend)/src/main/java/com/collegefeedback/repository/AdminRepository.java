package com.collegefeedback.repository;

import com.collegefeedback.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);

    Admin findByUsername(String username);

    List<Admin> findAllByOrderByIdAsc();

    boolean existsByUsernameOrEmail(String username, String email);
    boolean existsByUsernameOrEmailAndIdNot(String username, String email, int id);

}
