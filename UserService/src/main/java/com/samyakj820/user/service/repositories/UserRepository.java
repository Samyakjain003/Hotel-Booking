package com.samyakj820.user.service.repositories;

import com.samyakj820.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
