package com.example.demo.frontend.builder;

public interface Builder<T, Y> {

    T encode(Y uiBuilding);
    Y decode(T uiBuilding);

}
