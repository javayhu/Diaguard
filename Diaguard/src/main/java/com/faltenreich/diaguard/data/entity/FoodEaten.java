package com.faltenreich.diaguard.data.entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Faltenreich on 11.09.2016.
 */
public class FoodEaten extends BaseEntity {

    public class Column extends BaseEntity.Column {
        public static final String AMOUNT_IN_GRAMS = "amountInGrams";
        public static final String FOOD = "food";
        public static final String MEAL = "meal";
    }

    @DatabaseField(columnName = Column.AMOUNT_IN_GRAMS)
    private int amountInGrams;

    @DatabaseField(columnName = Column.FOOD, foreign = true, foreignAutoRefresh = true)
    private Food food;

    @DatabaseField(columnName = Column.MEAL, foreign = true, foreignAutoRefresh = true)
    private Meal meal;

    public int getAmountInGrams() {
        return amountInGrams;
    }

    public void setAmountInGrams(int amountInGrams) {
        this.amountInGrams = amountInGrams;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}