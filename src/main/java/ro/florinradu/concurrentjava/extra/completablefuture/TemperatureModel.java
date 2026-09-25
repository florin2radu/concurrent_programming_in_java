package ro.florinradu.concurrentjava.extra.completablefuture;

import com.google.gson.annotations.SerializedName;

public record TemperatureModel(
        @SerializedName("current_units") CurrentUnits currentUnits,
        Current current) {
}

record Current(
        @SerializedName("temperature_2m") float temperature) {
}

record CurrentUnits(
        @SerializedName("temperature_2m") String temperatureUnit) {
}
