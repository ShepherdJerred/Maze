package com.shepherdjerred.maze;

import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {

    private final char NUMBER_OF_GHOSTS = 4;
    private final char BLANK_CHAR = ' ';

    private boolean playing = true;
    private boolean gameWon = false;

    private List<String> lines = new ArrayList<>();
    private List<Powerup> powerups = new ArrayList<>();
    private List<Ghost> ghosts = new ArrayList<>();
    private Console console;
    private Player player;

    public Game(Console console, Player player) {
        this.console = console;
        this.player = player;
        createField();
    }

    public void createField() {
        for (int i = 0; i < 10000; i++) System.out.println();

        int width = jline.TerminalFactory.get().getWidth();
        int height = jline.TerminalFactory.get().getHeight();

        createGhosts();

        for (int i = 0; i < height; i++) {
            String line = "";
            for (int w = 0; w < width; w++) {
                double d = Math.random();

                if (d < 0.95) {
                    line = line.concat(String.valueOf(BLANK_CHAR));
                } else {
                    powerups.add(new Powerup(Powerup.PowerupType.POINT, w, i, 5, '*'));
                    line = line.concat(String.valueOf('*'));
                }
            }
            lines.add(line);
        }

        lines.set(height - 1, getScoreLine());
        lines.forEach(line -> console.printf(line));

    }

    public void createGhosts() {
        for (int i = 0; i < NUMBER_OF_GHOSTS; i++)
            ghosts.add(new Ghost(i, i, 50, 10));
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public String getScoreLine() {

        return "Score = " + player.getScore() + "          X: " + player.getX() + "   Y: " + player.getY() + "               Ghosts: " + ghosts.size();

    }

    public void seeIfAtPowerup() {

        Iterator<Powerup> p = powerups.iterator();
        while (p.hasNext()) {
            Powerup powerup = p.next();

            if (player.getX() == powerup.getX() && player.getY() == powerup.getY()) {
                player.doPowerUp(powerup);
                powerups.remove(powerup);
                return;
            }
        }

        if (powerups.size() == 0) {
            playing = false;
            gameWon = true;
        }
    }

    public void redraw() {

        for (int i = 0; i < jline.TerminalFactory.get().getHeight(); i++) System.out.println();

        int width = jline.TerminalFactory.get().getWidth();
        int height = jline.TerminalFactory.get().getHeight();

        // Fill the map with blank characters
        // Do this first so the blanks don't ever overwrite other rendered characters
        for (int i = 0; i < height; i++) {
            String line = "";
            for (int w = 0; w < width; w++)
                line = line.concat(String.valueOf(BLANK_CHAR));
            lines.set(i, line);
        }

        // Draw the powerups
        powerups.forEach(powerup -> {
            StringBuilder powerupLine = new StringBuilder(lines.get(powerup.getY()));
            powerupLine.setCharAt(powerup.getX(), '*');
            lines.set(powerup.getY(), powerupLine.toString());
        });

        // Draw the players spawn
        StringBuilder playerSpawn = new StringBuilder(lines.get(player.getSpawnY()));
        playerSpawn.setCharAt(player.getSpawnX(), 'S');
        lines.set(player.getSpawnY(), playerSpawn.toString());

        // Draw the ghosts
        // Do this second to last, so they aren't hidden by anything but the player
        ghosts.forEach(ghost -> {
            StringBuilder ghostLine = new StringBuilder(lines.get(ghost.getY()));
            ghostLine.setCharAt(ghost.getX(), '#');
            lines.set(ghost.getY(), ghostLine.toString());
        });

        // Draw the player
        // We do this last to ensure it's ALWAYS visible
        StringBuilder playerLine = new StringBuilder(lines.get(player.getY()));
        playerLine.setCharAt(player.getX(), '@');
        lines.set(player.getY(), playerLine.toString());

        lines.set(height - 1, getScoreLine());
        lines.forEach(line -> console.printf(line));

    }

    public void seeIfOnGhost() {
        ghosts.forEach(ghost -> {
            if (player.getX() == ghost.getX() && player.getY() == ghost.getY())
                playing = false;
        });

        if (powerups.size() == 0) {
            playing = false;
            gameWon = true;
        }
    }

    public void runGhostLogic() {

        long time = System.currentTimeMillis();

        ghosts.forEach(ghost -> {
            if (time - ghost.getLastMove() > ghost.getSpeed()) {
                ghost.moveTowardsPlayer();
            }
        });

    }

    public boolean isGameWon() {
        return gameWon;
    }

    public Player getPlayer() {
        return player;
    }
}
