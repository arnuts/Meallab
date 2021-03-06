

package com.example.meallab.storing_data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.meallab.Nutrients.Nutrient;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

/**
 * Allows for the storage of the following data about a single day:
 * - Recipes chosen
 * - Total nutrients
 * - Shopping list for that day.
 * - Date
 */
public class StoredDay implements Parcelable {

    /**
     * The recipe the user has(s)d chosen for this day.
     */
    public StoredRecipe[] recipes;
    /**
     * The date.
     */
    public LocalDate date;

    /**
     * The user can select a day as a favorite.
     */
    public boolean isFavorite = false;

    /**
     * Creates a new stored day for given date and nutrient goals.
     * that will be tracked.
     * @param date The date
     */
    public StoredDay(LocalDate date) {
        this.date = date;
        this.recipes = new StoredRecipe[0];
    }

    /**
     * Gets the accumulated nutritional values for all recipes in this day.
     * @return The acc nutritional value for this day.
     */
    public Nutrient[] getTotalNutrients() {
        ArrayList<String> nutrientNames = new ArrayList<>();
        ArrayList<Nutrient> collectiveNutrients = new ArrayList<>();
        // First add all nutrient names.
        for (StoredRecipe recipe : this.recipes) {
            for (Nutrient nut : recipe.nutrients) {
                if (!nutrientNames.contains(nut.name)) {
                    nutrientNames.add(nut.name);
                }
                collectiveNutrients.add(nut);
            }
        }
        Nutrient[] result = new Nutrient[nutrientNames.size()];

        // Then increment the amounts.
        for (int i = 0; i < nutrientNames.size(); i++) {
            String name = nutrientNames.get(i);
            Nutrient nut = new Nutrient();
            nut.name = name;
            ArrayList<Nutrient> toRemove = new ArrayList<>();
            for (Nutrient collNut : collectiveNutrients) {
                if (nut.name.equals(collNut.name)) {
                    nut.amount += collNut.amount;
                    nut.amountDailyTarget = collNut.amountDailyTarget;
                    nut.unit = collNut.unit;
                    toRemove.add(collNut);
                }
            }
            collectiveNutrients.removeAll(toRemove);
            result[i] = nut;
        }
        return result;
    }

    // ---- Parcelable ----

    protected StoredDay(Parcel in) {
        recipes = (StoredRecipe[]) in.readArray(StoredRecipe.class.getClassLoader());
        date = (LocalDate) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(recipes);
        dest.writeSerializable(date);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StoredDay> CREATOR = new Parcelable.Creator<StoredDay>() {
        @Override
        public StoredDay createFromParcel(Parcel in) {
            return new StoredDay(in);
        }

        @Override
        public StoredDay[] newArray(int size) {
            return new StoredDay[size];
        }
    };
}
