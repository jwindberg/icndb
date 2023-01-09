package com.marsraver.icndb.ui;

public class UiMain {

    public static void main(String[] args) {
        Dog dog = new Dog();
        Cat cat = new Cat();

        Listener listener = new Listener();
        listener.register(Dog::bark);
        listener.register(cat.meow());

    }
}
