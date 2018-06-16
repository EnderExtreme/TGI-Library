package com.rong.tgi.temperature.handling;

public class Temperature implements ITemperature {

    private float temperature = 26.0F;

    public void delTemperature(float points) {
        this.temperature -= points;
    }

    public void addTemperature(float points) {
        this.temperature += points;
    }

    public void setTemperature(float points) {
        this.temperature = points;
    }

    public float getTemperature() {
        return this.temperature;
    }
}
