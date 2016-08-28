package com.shepherdjerred.maze.objects;

public class Ghost extends MapObject {

    private long lastMove;
    private int moveFails;
    private int speed;
    private int smartness;

    public Ghost(int x, int y, int speed, int smartness) {
        super(x, y, '¤');
        lastMove = 0;
        moveFails = 0;
        this.speed = speed;
        this.smartness = smartness;
    }

    public long getLastMove() {
        return lastMove;
    }

    public void setLastMove(long lastMove) {
        this.lastMove = lastMove;
    }

    public int getMoveFails() {
        return moveFails;
    }

    public void setMoveFails(int moveFails) {
        this.moveFails = moveFails;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSmartness() {
        return smartness;
    }
}
