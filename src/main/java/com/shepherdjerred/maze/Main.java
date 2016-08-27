package com.shepherdjerred.maze;

import jline.console.ConsoleReader;

import java.io.Console;
import java.io.IOException;

public class Main {

    public static Game game;

    public static void main(String[] args) {

        try {

            ConsoleReader cr;
            Console c = System.console();
            int width = jline.TerminalFactory.get().getWidth();
            int height = jline.TerminalFactory.get().getHeight();

            long lastMove = System.currentTimeMillis();

            Player player = new Player(width / 2, height / 2);
            game = new Game(c, player);

            game.redraw();

            cr = new ConsoleReader();

            while (game.isPlaying()) {
                game.runGhostLogic();

                if (cr.getInput() != null) {
                    int read = cr.readCharacter();

                    if (System.currentTimeMillis() - lastMove > 50)
                        lastMove = System.currentTimeMillis();
                    else {
                        continue;
                    }

                    if (read == 's') {
                        if (player.getY() < height - 2) {
                            player.increaseY();
                            game.redraw();
                            game.seeIfOnGhost();
                            game.seeIfAtPowerup();
                        }
                    }

                    if (read == 'a') {
                        if (player.getX() > 0) {
                            player.decreaseX();
                            game.redraw();
                            game.seeIfOnGhost();
                            game.seeIfAtPowerup();
                        }
                    }

                    if (read == 'w') {
                        if (player.getY() > 0) {
                            player.decreaseY();
                            game.redraw();
                            game.seeIfOnGhost();
                            game.seeIfAtPowerup();
                        }
                    }

                    if (read == 'd') {
                        if (player.getX() < width - 1) {
                            player.increaseX();
                            game.redraw();
                            game.seeIfOnGhost();
                            game.seeIfAtPowerup();
                        }
                    }
                }
            }

            if (game.isGameWon()) {
                c.printf("You won!");
            } else {
                c.printf("You lost :(");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

}
