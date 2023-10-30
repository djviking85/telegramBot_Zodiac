package com.telebotZodiac.bot.service;

import com.telebotZodiac.bot.entity.Shelter;
import com.telebotZodiac.bot.repository.ShelterRepository;
import com.telebotZodiac.bot.shelter.ShelterGoroskop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Optional;

/**
 * This class implements working with a database to receive or receive the necessary information
 */
@Slf4j
@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    /**
     * Method for obtaining information about the nursery, depending on the selected type
     *
     * @param type - Shelter type
     */
    public String getDescription(ShelterGoroskop type) {
        Optional<Shelter> shelter = shelterRepository.findFirstByType(type);
        if (shelter.isPresent()) {
            return shelter.get().getDescription();
        }
        log.info("запускаем getInfo");
        return "Тут заглушка на getInfo :(";
    }

    public String getInstruction(ShelterGoroskop type) {
        Optional<Shelter> shelter = shelterRepository.findFirstByType(type);
        if (shelter.isPresent()) {
            return shelter.get().getInstruction();
        }
        log.info("в базе данных нет питомников с таким животным");
        return "Тут пока что пусто :(";
    }
}
