package com.selfProjectbook.SelfProjecttwoBook.repository;

import com.selfProjectbook.SelfProjecttwoBook.entity.User;
import com.selfProjectbook.SelfProjecttwoBook.entity.type.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    Optional<User> findByProviderIdAndAuthProviderType(String provideId, AuthProviderType authProviderType);

}
