package com.telebotZodiac.bot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

    @Entity
    public class NotificationTask {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private long id;
        private long chatId;
        private String task;
        private LocalDateTime dateTime;

        public NotificationTask(long chatId, LocalDateTime dateTime, String task) {
            this.chatId = chatId;
            this.task = task;
            this.dateTime = dateTime;
        }

        public NotificationTask() {

        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Long getChatId() {
            return chatId;
        }

        public void setChatId(Long chatId) {
            this.chatId = chatId;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NotificationTask that = (NotificationTask) o;
            return id == that.id && Objects.equals(chatId, that.chatId) && Objects.equals(task, that.task) && Objects.equals(dateTime, that.dateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, chatId, task, dateTime);
        }

        @Override
        public String toString() {
            return "NotificationTask{" +
                    "id=" + id +
                    ", chatId=" + chatId +
                    ", task='" + task + '\'' +
                    ", dateTime=" + dateTime +
                    '}';
        }
    }

