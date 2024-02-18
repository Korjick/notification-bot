package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;

public abstract class Command {
    private Command next;

    public Command setNext(Command next) {
        this.next = next;
        return this;
    }

    public void handle(Update update) {
        if (!handleCommand(update) && next != null) {
            next.handle(update);
        }
    }

    public abstract String name();
    protected abstract boolean handleCommand(Update update);
}
