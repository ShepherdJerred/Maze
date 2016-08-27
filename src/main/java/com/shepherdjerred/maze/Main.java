package com.shepherdjerred.maze;

import jline.console.ConsoleReader;

import java.io.Console;
import java.io.IOException;

public class Main {

    public static Game game;
    private static long lastMove;

    public static void main(String[] args) {

        lastMove = System.currentTimeMillis();

        Console c = System.console();
        int width = jline.TerminalFactory.get().getWidth();
        int height = jline.TerminalFactory.get().getHeight();

        Player player = new Player(width / 2, height / 2);
        game = new Game(c, player);

        game.redraw();

        while (game.isPlaying()) {
            game.runGhostLogic();
            listenForKeys();
        }

        if (game.isGameWon()) {
            c.printf("You won!");
        } else {
            c.printf("You lost :(");
        }

    }

    static void listenForKeys() {

        ConsoleReader cr;
        int width = jline.TerminalFactory.get().getWidth();
        int height = jline.TerminalFactory.get().getHeight();

        try {
            cr = new ConsoleReader();


            if (cr.getInput() != null) {
                int read = cr.readCharacter();

                if (System.currentTimeMillis() - lastMove > 50)
                    lastMove = System.currentTimeMillis();
                else
                    return;

                if (read == 's') {
                    if (game.getPlayer().getY() < height - 2) {
                        game.getPlayer().increaseY();
                        game.redraw();
                        game.seeIfOnGhost();
                        game.seeIfAtPowerup();
                    }
                }

                if (read == 'a') {
                    if (game.getPlayer().getX() > 0) {
                        game.getPlayer().decreaseX();
                        game.redraw();
                        game.seeIfOnGhost();
                        game.seeIfAtPowerup();
                    }
                }

                if (read == 'w') {
                    if (game.getPlayer().getY() > 0) {
                        game.getPlayer().decreaseY();
                        game.redraw();
                        game.seeIfOnGhost();
                        game.seeIfAtPowerup();
                    }
                }

                if (read == 'd') {
                    if (game.getPlayer().getX() < width - 1) {
                        game.getPlayer().increaseX();
                        game.redraw();
                        game.seeIfOnGhost();
                        game.seeIfAtPowerup();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
