package com.example.vandraby.information;

import java.util.Vector;

public class DatabaseMock implements DatabaseBase {

    @Override
    public User getUser() {
        return createFakeUser();
    }

    private User createFakeUser() {
        User user = new User("Рома", "Клевжиц", "@r.klevzhits");

        String correctUrl = "https://sun9-21.userapi.com/impf/c637126/v637126533/10b43/3gEczuIgifs.jpg?size=1760x1707&quality=96&sign=d612a7bba1c66f1c72709fa64b2f9623&type=album";

        user.setPhotoUrl(correctUrl);

        user.setFollowersNumber(14);
        user.setFollowingNumber(88);

        Vector<Achievement> achievements = new Vector<Achievement>();
        achievements.add(new Achievement(correctUrl, "пёс"));
        achievements.add(new Achievement(correctUrl, "лох"));
        achievements.add(new Achievement(correctUrl, "чмо"));
        achievements.add(new Achievement(correctUrl, "хуй заморский"));
        achievements.add(new Achievement(correctUrl, "говно"));
        achievements.add(new Achievement(correctUrl, "урод"));
        achievements.add(new Achievement(correctUrl, "поц"));
        achievements.add(new Achievement(correctUrl, "хер"));
        user.setAchievements(achievements);

        Vector<Trip> trips = new Vector<Trip>();
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        user.setTrips(trips);

        return user;
    }
}
