package com.shepherdjerred.maze;

import com.shepherdjerred.maze.objects.*;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    public final int GHOST_COUNT = 40;
    private int status = 0;
    private Player player;

    private List<String> gameLines;
    private List<MapObject> mapObjects;

    Game() {
        gameLines = new ArrayList<>();
        mapObjects = new ArrayList<>();
        player = new Player(Main.getConsoleWidth() / 2, Main.getConsoleHeight() / 2);
        mapObjects.add(player);
        initializeMap();
        renderGame();
    }

    void initializeMap() {
        for (int y = 0; y < Main.getConsoleHeight(); y++) {
            String line = "";
            for (int x = 0; x < Main.getConsoleWidth(); x++) {
                double d = Math.random();
                line = line.concat(String.valueOf(' '));
                if (d < .15) {
                    mapObjects.add(new Barrier(x, y));
                } else if (d < .175) {
                    mapObjects.add(new Powerup(x, y, '·', Powerup.Type.POINT, 5));
                }

            }
            gameLines.add(line);
        }

        for (int i = 0; i < GHOST_COUNT; i++)
            mapObjects.add(new Ghost(
                    ThreadLocalRandom.current().nextInt(0, 10 + 1),
                    ThreadLocalRandom.current().nextInt(0, 10 + 1),
                    ThreadLocalRandom.current().nextInt(350, 550 + 1),
                    ThreadLocalRandom.current().nextInt(3, 7 + 1)
            ));
    }

    void renderGame() {

        System.out.println();

        // Fill the map with blank characters
        // Do this first so the blanks don't ever overwrite other rendered characters
        for (int y = 0; y < Main.getConsoleHeight(); y++) {
            String line = "";
            for (int x = 0; x < Main.getConsoleWidth(); x++)
                line = line.concat(" ");
            gameLines.set(y, line);
        }

        // Add the map objects
        List<MapObject> mapObjectsCopy = new ArrayList<>(mapObjects);

        mapObjectsCopy.forEach(mapObject -> {
            StringBuilder objectLine = new StringBuilder(gameLines.get(mapObject.getY()));
            objectLine.setCharAt(mapObject.getX(), mapObject.getCharacter());
            gameLines.set(mapObject.getY(), objectLine.toString());
        });

        // Explicitly render the player
        // This ensures they're always on top
        StringBuilder objectLine = new StringBuilder(gameLines.get(player.getY()));
        objectLine.setCharAt(player.getX(), player.getCharacter());
        gameLines.set(player.getY(), objectLine.toString());

        // Output the lines to console
        gameLines.set(Main.getConsoleHeight() - 1, getScoreLine());
        gameLines.forEach(line -> Main.getConsole().printf(line));

    }

    String getScoreLine() {
        int powerupCount = 0;
        for (MapObject mapObject : mapObjects)
            if (mapObject instanceof Powerup)
                powerupCount++;

        return " Score = " + player.getScore() + "          X: " + player.getX() + "   Y: " + player.getY() + "          Ghosts: " + GHOST_COUNT + "          Remaining Powerups: " + powerupCount;
    }

    void runGameLoop() {
        runGhostLogic();

        while (status == 0)
            listenForKeys();
    }

    void runGhostLogic() {
        Thread ai = new Thread() {
            public void run() {
                while (status == 0) {
                    List<MapObject> mapObjectsCopy = new ArrayList<>(mapObjects);

                    mapObjectsCopy.forEach(mapObject -> {
                        if (mapObject instanceof Ghost) {

                            Ghost ghost = (Ghost) mapObject;
                            if (System.currentTimeMillis() - ghost.getLastMove() > ghost.getSpeed()) {

                                int newY = mapObject.getY();
                                int newX = mapObject.getX();

                                if (ghost.getMoveFails() < ghost.getSmartness()) {
                                    if (player.getX() > ghost.getX())
                                        newX += 1;
                                    if (player.getY() > ghost.getY())
                                        newY += 1;
                                    if (player.getX() < ghost.getX())
                                        newX -= 1;
                                    if (player.getY() < ghost.getY())
                                        newY -= 1;
                                } else {
                                    double d = Math.random();
                                    if (d < 0.5) {
                                        newX += 1;
                                        newY += 1;
                                    } else {
                                        newX -= 1;
                                        newY -= 1;
                                    }
                                }

                                if (newX != mapObject.getX() && newY != mapObject.getY()) {
                                    double d = Math.random();

                                    // 50% chance of either the X or Y being changed
                                    // This ensures the ghosts never make two moves at once
                                    if (d < 0.5) {
                                        newX = ghost.getX();
                                    } else {
                                        newY = ghost.getY();
                                    }
                                }

                                if (checkNoCollision(ghost, newX, newY)) {
                                    ghost.setMoveFails(ghost.getMoveFails() - 1);
                                    ghost.setLastMove(System.currentTimeMillis());
                                    mapObject.setX(newX);
                                    mapObject.setY(newY);
                                    checkGhostCollision();
                                    renderGame();
                                } else {
                                    ghost.setMoveFails(ghost.getMoveFails() + 1);
                                }

                            }
                        }
                    });
                }
            }
        };

        ai.start();
    }

    void checkGhostCollision() {

        mapObjects.forEach(mapObject -> {

            if (mapObject instanceof Ghost) {
                Ghost ghost = (Ghost) mapObject;
                if (player.getX() == ghost.getX() && player.getY() == ghost.getY())
                    status = 2;
            }

        });

    }

    void listenForKeys() {

        ConsoleReader cr = null;

        try {
            cr = new ConsoleReader();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (cr.getInput() != null) {
            int read = 0;

            try {
                read = cr.readCharacter();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (System.currentTimeMillis() - player.getLastMove() < 75)
                return;

            int newX = player.getX();
            int newY = player.getY();

            if (read == 's')
                newY++;

            if (read == 'a')
                newX--;

            if (read == 'w')
                newY--;

            if (read == 'd')
                newX++;

            if (checkNoCollision(player, newX, newY) && status == 0) {
                player.setLastMove(System.currentTimeMillis());
                player.setX(newX);
                player.setY(newY);
                checkGhostCollision();
                checkPowerupCollision();
                renderGame();
            }
        }
    }

    boolean checkNoCollision(MapObject mapObject, int newX, int newY) {

        if (newX < 0 || newY < 0 || newX > Main.getConsoleWidth() - 1 || newY > Main.getConsoleHeight() - 2)
            return false;

        List<MapObject> mapObjectsCopy = new ArrayList<>(mapObjects);

        for (MapObject object : mapObjectsCopy) {
            if (object instanceof Powerup || object instanceof Player)
                continue;
            if (newX == object.getX() && newY == object.getY()) {
                if (mapObject instanceof Ghost && object instanceof Ghost)
                    return false;
                return false;
            }
        }

        return true;

    }

    void checkPowerupCollision() {

        Powerup powerup = null;

        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof Powerup && mapObject.getX() == player.getX() && mapObject.getY() == player.getY())
                powerup = (Powerup) mapObject;
        }

        if (powerup != null) {
            mapObjects.remove(powerup);
            player.runPowerup(powerup);
        }

    }

    public int getStatus() {
        return status;
    }
}
