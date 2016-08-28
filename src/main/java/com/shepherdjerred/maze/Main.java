package com.shepherdjerred.maze;

import java.io.Console;

public class Main {

    private static Console console;
    private static int consoleHeight;
    private static int consoleWidth;

    public static void main(String[] args) {

        for (int i = 0; i < 10000; i++) System.out.println();

        console = System.console();
        consoleHeight = jline.TerminalFactory.get().getHeight();
        consoleWidth = jline.TerminalFactory.get().getWidth();

        Game game = new Game();

        while (game.getStatus() == 0) {
            game.runGameLoop();
        }

        if (game.getStatus() == 1) {
            console.printf("You win!");
        } else {
            console.printf("You lose :(");
        }

    }

    public static Console getConsole() {
        return console;
    }

    public static int getConsoleHeight() {
        return consoleHeight;
    }

    public static int getConsoleWidth() {
        return consoleWidth;
    }
}
