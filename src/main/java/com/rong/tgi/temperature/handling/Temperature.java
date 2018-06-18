package com.rong.tgi.temperature.handling;

public class Temperature implements ITemperature {

    private float temperature = 1.0F;

    public void decrease(float points) { this.temperature -= points; if(this.temperature < -100F) this.temperature = -100F; }

    public void increase(float points) { this.temperature += points; if(this.temperature > 100F) this.temperature = 100F; }

    public void set(float points) {
        this.temperature = points;
    }

    public float get() { return this.temperature; }
}
