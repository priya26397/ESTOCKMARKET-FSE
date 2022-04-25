package com.stockmarket.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.user.dto.UserDTO;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Integer> {

	Optional<UserDTO> findByEmail(String email);

	UserDTO findByUserName(String userName);

}
