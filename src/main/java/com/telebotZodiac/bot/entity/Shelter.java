package com.telebotZodiac.bot.entity;

import com.telebotZodiac.bot.shelter.ShelterGoroskop;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;



/**
 * This class represents basic information about the shelter
 * and its relationship to the collection of Animal objects that are in this shelter.
 * It is used to work with shelter data,
 * including creating, reading, updating and deleting shelters from the database.
 */
@Entity
@Data
public class Shelter {
    @Id
    @Enumerated(EnumType.STRING)
    private ShelterGoroskop type;
    private String name;
    private String description;
    private String instruction;


}
