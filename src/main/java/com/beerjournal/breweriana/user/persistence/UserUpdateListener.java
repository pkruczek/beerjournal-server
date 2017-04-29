package com.beerjournal.breweriana.user.persistence;

import com.beerjournal.breweriana.user.User;

public interface UserUpdateListener {

    default void onInsert(User item) {}

    default void onUpdate(User item) {}

    default void onDelete(User item) {}

}
