package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {
    @Id
    @GeneratedValue()
    private Integer firstKey;
    private Long chatId;
    private String message;
    private LocalDateTime dateMessage;

    public NotificationTask(Integer firstKey, Long chatId, String message, LocalDateTime dateMessage) {
        this.firstKey = firstKey;
        this.chatId = chatId;
        this.message = message;
        this.dateMessage = dateMessage;
    }

    public NotificationTask() {
    }

    public Integer getFirstKey() {
        return firstKey;
    }

    public void setFirstKey(Integer firstKey) {
        this.firstKey = firstKey;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(LocalDateTime dateMessage) {
        this.dateMessage = dateMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(firstKey, that.firstKey) && Objects.equals(chatId, that.chatId) && Objects.equals(message, that.message) && Objects.equals(dateMessage, that.dateMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstKey, chatId, message, dateMessage);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "firstKey=" + firstKey +
                ", chatId=" + chatId +
                ", message='" + message + '\'' +
                ", dateMessage=" + dateMessage +
                '}';
    }
}