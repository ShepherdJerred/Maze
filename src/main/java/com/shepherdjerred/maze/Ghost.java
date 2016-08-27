package com.shepherdjerred.maze;

public class Ghost {

    private int x;
    private int y;
    private int speed;
    private int smart;
    private long lastMove;

    public Ghost(int x, int y, int speed, int smart) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.smart = smart;
        lastMove = System.currentTimeMillis();
    }

    public long getLastMove() {
        return lastMove;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSmart() {
        return smart;
    }

    void moveTowardsPlayer() {
        Player player = Main.game.getPlayer();

        double d = Math.random();

        if (player.getX() > x)
            x++;
        if (player.getY() > y)
            y++;
        if (player.getX() < x)
            x--;
        if (player.getY() < y)
            x--;

        lastMove = System.currentTimeMillis();
        Main.game.redraw();
    }
}
