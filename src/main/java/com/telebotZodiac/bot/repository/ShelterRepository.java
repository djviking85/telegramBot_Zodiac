package com.telebotZodiac.bot.repository;

import com.telebotZodiac.bot.entity.Shelter;
import com.telebotZodiac.bot.shelter.ShelterGoroskop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Optional<Shelter> findFirstByType(ShelterGoroskop type);
}
