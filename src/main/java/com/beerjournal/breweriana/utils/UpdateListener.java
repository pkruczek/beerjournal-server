package com.beerjournal.breweriana.utils;

public interface UpdateListener<T> {

    default void onInsert(T t) {}

    default void onUpdate(T t) {}

    default void onDelete(T t) {}
}
