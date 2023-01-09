package com.marsraver.icndb.ui;


import java.util.function.Supplier;

public class Listener {


    public void onEvent() {
        listeners.forEach(e -> e.get());
    }

    public void register(Supplier<Void> listener) {
        listeners.add(listener);
    }
}
