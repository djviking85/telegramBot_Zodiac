package com.telebotZodiac.bot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

public class User {
    @Entity
    @Data
    public class Users {
        @Id
        @GeneratedValue
        private Long id;
        private String name;
        private Long chatId;
    }
}
