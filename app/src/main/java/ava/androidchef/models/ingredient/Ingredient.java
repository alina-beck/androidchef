package ava.androidchef.models.ingredient;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private int id;
    private String name;
    private String unit;

    public Ingredient(String name, String unit) {
        this(-1, name, unit);
    }

    public Ingredient(int id, String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(unit);
    }

    private Ingredient(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.unit = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
