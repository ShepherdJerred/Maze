package com.shepherdjerred.maze;

public class Powerup {

    PowerupType powerupType;
    int x;
    int y;
    int modifier;
    char character;

    public Powerup(PowerupType powerupType, int x, int y, int modifier, char character) {
        this.powerupType = powerupType;
        this.x = x;
        this.y = y;
        this.modifier = modifier;
        this.character = character;
    }

    public int getModifier() {
        return modifier;
    }

    public char getCharacter() {
        return character;
    }

    public PowerupType getPowerupType() {
        return powerupType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public enum PowerupType {
        POINT
    }

}
