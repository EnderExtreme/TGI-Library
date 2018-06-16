package com.rong.tgi.temperature.handling;

public interface ITemperature {

    void addTemperature(float points);

    void setTemperature(float points);

    void delTemperature(float points);

    float getTemperature();
}
