package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repositoties.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private SendMessage message;
    private SendResponse response;
    private Pattern pattern;
    private String date;
    private String messageText;
    private Long chatId;
    private NotificationTask notificationTask;
    @Autowired
    private TelegramBot telegramBot;
    private final NotificationTaskRepository notificationTaskRepository;

    public TelegramBotUpdatesListener(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            message = new SendMessage(update.message().chat().id(), "Welcome!");
            if (update.message().text().equals("/start")) {
                response = telegramBot.execute(message);
            }
            pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
            Matcher matcher = pattern.matcher(update.message().text());
            if (matcher.matches()) {
                date = matcher.group(1);
                messageText = matcher.group(3);
                chatId = update.message().chat().id();
                notificationTask = new NotificationTask(
                        0,
                        update.message().chat().id(),
                        messageText,
                        LocalDateTime.parse(date,
                                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                );
                notificationTaskRepository.save(notificationTask);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(fixedDelay = 60_000L)
    public void sendMessage() {
        List<NotificationTask> notificationTasksList = notificationTaskRepository.getDateMessageNow().stream().toList();
        if (!notificationTasksList.isEmpty()) {
            for (int i = 0; i < notificationTasksList.size(); i++) {
                SendMessage sendMessage = new SendMessage(notificationTasksList.get(i).getChatId(),
                        notificationTasksList.get(i).getMessage());
                response = telegramBot.execute(sendMessage);
            }
        }
    }
}