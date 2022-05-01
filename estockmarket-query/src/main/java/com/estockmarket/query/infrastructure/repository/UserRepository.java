package com.estockmarket.query.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.estockmarket.query.domain.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

	Optional<User> findByUserName(String userName);

}
