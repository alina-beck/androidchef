package ava.androidchef.models.ingredient;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private long id;
    private String name;
    private String unit;

    public Ingredient(String name, String unit) {
        this(-1, name, unit);
    }

    public Ingredient(long id, String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeLong(id);
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ingredient that = (Ingredient) o;

        return name.equals(that.name) && unit.equals(that.unit);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + unit.hashCode();
        return result;
    }
}
