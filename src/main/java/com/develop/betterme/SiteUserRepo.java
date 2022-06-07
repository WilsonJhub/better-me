package com.develop.betterme;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepo extends JpaRepository<SiteUser, Long > {
    SiteUser findByUsername(String username);
}
