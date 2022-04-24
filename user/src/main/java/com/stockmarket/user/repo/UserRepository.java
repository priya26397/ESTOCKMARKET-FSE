package com.stockmarket.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.user.dto.UserDTO;

@Repository
public interface UserRepository extends MongoRepository<UserDTO, Integer> {

	UserDTO findByUserName(String userName);

}
