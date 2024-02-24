package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.utils.CommandParser;
import edu.java.bot.core.dto.UserData;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends Command {

    private final List<Command> commands;

    @Autowired
    public HelpCommand(MessageSource messageSource, Map<Long, UserData> trackHandler, CommandParser commandParser, List<Command> commands) {
        super(messageSource, trackHandler, commandParser);
        this.commands = commands;
    }

    @Override
    public String name() {
        return "/help";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        UserData data = trackHandler.get(chatId);

        StringBuilder stringBuilder = new StringBuilder();
        commands.forEach(command -> formatHelp(stringBuilder, command.name(), data.locale()));
        formatHelp(stringBuilder, name(), data.locale());
        stringBuilder.insert(
            0,
            messageSource.getMessage("command.help", null, data.locale())
        );
        return new SendMessage(chatId, stringBuilder.toString());
    }

    private void formatHelp(StringBuilder builder, String name, Locale locale) {
        builder.append("\n%s%s%s".formatted(
            name,
            CommandParser.MESSAGE_SPLITTER,
            messageSource.getMessage(
                "command.%s.description".formatted(commandParser.peelCommandName(name)),
                null,
                locale
            )
        ));
    }
}
