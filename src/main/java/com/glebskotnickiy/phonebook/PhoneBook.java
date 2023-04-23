package com.glebskotnickiy.phonebook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PhoneBook {

    private List<User> users = new ArrayList<>();

    public List<User> getSortedUsersByLetter() {
        List<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort(Comparator.comparing(user -> user.getName().substring(0, 1)));
        return sortedUsers;
    }

    public List<User> getUsers() {
        return users;
    }
}
