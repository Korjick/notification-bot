package edu.java.bot.core;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.service.CommandHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
@Slf4j
public class BotImpl implements Bot {

    private final TelegramBot telegramBot;
    private final CommandHandler commandHandler;

    @Override
    @PostConstruct
    public void start() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            AbstractSendRequest<? extends AbstractSendRequest<?>> request = commandHandler.handle(update);
            if (!Objects.isNull(request))
                execute(request);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
