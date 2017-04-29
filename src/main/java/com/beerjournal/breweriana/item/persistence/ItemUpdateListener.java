package com.beerjournal.breweriana.item.persistence;

public interface ItemUpdateListener {

    default void onInsert(Item item) {}

    default void onUpdate(Item item) {}

    default void onDelete(Item item) {}

}
