package com.scnsoft.bot.repository;

import java.security.interfaces.RSAPrivateKey;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scnsoft.bot.entity.Utility;
import com.scnsoft.bot.entity.Utility.Key;

@Repository
public interface UtilRepository extends JpaRepository<Utility, String> {

}
