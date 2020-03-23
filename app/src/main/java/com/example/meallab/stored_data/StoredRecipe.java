package com.example.meallab.stored_data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.meallab.Nutrients.Nutrient;

/**
 * Class that allows for the storing of recipes.
 */
public class StoredRecipe implements Parcelable {
    /**
     * The name (title of this recipe)
     */
    String name;
    /**
     * The macro nutrients in this recipe (4 objects).
     */
    Nutrient[] macroNutrients;
    /**
     * The amount of minutes it takes to cook this recipe.
     */
    int cookingMins;
    /**
     * The number of people this recipe serves.
     */
    int numberOfServings;
    /**
     * The price per serving.
     */
    float pricePerServing;
    /**
     * The id of the Spoonacular recipe.
     */
    int recipeID;
    /**
     * True if the user has favorited this recipe, false otherwise.
     */
    boolean isFavorite;

    // ---- Parcelable ----

    protected StoredRecipe(Parcel in) {
        name = in.readString();
        macroNutrients = (Nutrient[]) in.readArray(Nutrient.class.getClassLoader());
        cookingMins = in.readInt();
        numberOfServings = in.readInt();
        pricePerServing = in.readFloat();
        recipeID = in.readInt();
        isFavorite = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeArray(macroNutrients);
        dest.writeInt(cookingMins);
        dest.writeInt(numberOfServings);
        dest.writeFloat(pricePerServing);
        dest.writeInt(recipeID);
        dest.writeByte((byte) (isFavorite ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StoredRecipe> CREATOR = new Parcelable.Creator<StoredRecipe>() {
        @Override
        public StoredRecipe createFromParcel(Parcel in) {
            return new StoredRecipe(in);
        }

        @Override
        public StoredRecipe[] newArray(int size) {
            return new StoredRecipe[size];
        }
    };
}