package edu.java.bot.core;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.command.Command;
import edu.java.bot.command.InitialCommand;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class BotImpl implements Bot {

    private final TelegramBot telegramBot;
    private final Command.Handler commandHandler;

    @Override
    @PostConstruct
    public void start() {
        telegramBot.setUpdatesListener(this);
        log.info(Link.parseURI("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c?test=1&a=b").toString());
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> commandHandler.handle(this, update));
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
