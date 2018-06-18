package com.rong.tgi.temperature.handling;

public interface ITemperature {

    void increase(float points);

    void set(float points);

    void decrease(float points);

    float get();
}
