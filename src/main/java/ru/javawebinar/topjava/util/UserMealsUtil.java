package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2019, Month.SEPTEMBER, 28, 11, 59), "Обед", 2001)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        for (UserMealWithExceed meal : list) {
            System.out.println(meal.toString());
        }
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> totalCaloriesPerDay = new HashMap<>();
        for (UserMeal meal : mealList) {
            totalCaloriesPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (oldVal, newVal) -> oldVal + newVal);
        }

        List<UserMealWithExceed> mealWithExceedList = new ArrayList<>();
        for (UserMeal meal : mealList) {
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime))
                mealWithExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        totalCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
        }

        return mealWithExceedList;
    }
}
