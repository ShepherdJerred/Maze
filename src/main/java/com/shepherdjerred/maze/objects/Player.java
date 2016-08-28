package com.shepherdjerred.maze.objects;

public class Player extends MapObject {

    private int spawnX;
    private int spawnY;
    private int score;
    private long lastMove;

    public Player(int x, int y) {
        super(x, y, '@');
        spawnX = x;
        spawnY = y;
        score = 0;
        lastMove = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public long getLastMove() {
        return lastMove;
    }

    public void setLastMove(long lastMove) {
        this.lastMove = lastMove;
    }

    public void runPowerup(Powerup powerup) {

        switch (powerup.getType()) {

            case POINT:
                score += powerup.getModifier();
                break;
            case EAT:
                break;

        }

    }
}
