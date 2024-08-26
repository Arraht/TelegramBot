package pro.sky.telegrambot.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.Collection;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Integer> {
    @Query(value = "SELECT * FROM notification_task WHERE date_message = date_trunc('minute', CURRENT_TIMESTAMP(0))",
            nativeQuery = true)
    Collection<NotificationTask> getDateMessageNow();
}