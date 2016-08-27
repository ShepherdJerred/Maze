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

        if (player.getX() > x)
            isMoveValid(x + 1, y);
        if (player.getY() > y)
            isMoveValid(x, y + 1);
        if (player.getX() < x)
            isMoveValid(x - 1, y);
        if (player.getY() < y)
            isMoveValid(x, y - 1);

    }

    private void isMoveValid(int x, int y) {
        boolean valid = true;

        for (Ghost ghost : Main.game.getGhosts()) {
            if (ghost == this)
                continue;
            if (ghost.getX() == x && ghost.getY() == y)
                valid = false;
        }

        if (valid) {
            this.x = x;
            this.y = y;
            lastMove = System.currentTimeMillis();
            Main.game.redraw();
            Main.game.seeIfOnGhost();
        }

    }
}
