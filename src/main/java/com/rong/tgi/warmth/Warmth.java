package com.rong.tgi.warmth;

public class Warmth implements IWarmth {

    private float warmth = 1.0F;

    public void delWarmth(float points) {
        this.warmth -= points;
    }

    public void addWarmth(float points) {
        this.warmth += points;
    }

    public void setWarmth(float points) {
        this.warmth = points;
    }

    public float getWarmth() {
        return this.warmth;
    }
}
