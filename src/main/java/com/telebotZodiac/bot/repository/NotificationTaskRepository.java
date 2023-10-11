package com.telebotZodiac.bot.repository;

import com.telebotZodiac.bot.model.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository  extends JpaRepository<NotificationTask, Long> {
    List<NotificationTask> findByDateTime(LocalDateTime now);
}
