package com.shepherdjerred.maze;

public class Player {

    private int spawnX;
    private int spawnY;
    private int x;
    private int y;
    private int score;

    Player(int x, int y) {
        this.x = x;
        this.y = y;
        spawnX = x;
        spawnY = y;
        score = 0;
    }

    public void doPowerUp(Powerup powerup) {
        if (powerup.getPowerupType() == Powerup.PowerupType.POINT)
            this.score += powerup.getModifier();
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void increaseX() {
        x++;
    }

    public void increaseY() {
        y++;
    }

    public void decreaseX() {
        x--;
    }

    public void decreaseY() {
        y--;
    }
}
