package com.telebotZodiac.bot.service;

import com.telebotZodiac.bot.entity.Shelter;
import com.telebotZodiac.bot.repository.ShelterRepository;
import com.telebotZodiac.bot.shelter.ShelterGoroskop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Slf4j
@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public String getDescription(ShelterGoroskop type) {
        Optional<Shelter> shelter = shelterRepository.findFirstByType(type);
        if (shelter.isPresent()) {
            return shelter.get().getDescription();
        }
        log.info("запускаем Дескрипшн (инфо) гороскопа");
        return "Тут заглушка на getInfo :(";
    }

    public String getInstruction(ShelterGoroskop type) {
        Optional<Shelter> shelter = shelterRepository.findFirstByType(type);
        if (shelter.isPresent()) {
            return shelter.get().getInstruction();
        }
        log.info("Запускаем по дате рождения методы");
        return "Тут пока что пусто :(";
    }
}
