package ro.florinradu.concurrentjava.extra.completablefuture;

public enum City {
    CLUJ_NAPOCA("Cluj-Napoca", "46.75", "23.625"),
    SATU_MARE("Satu Mare", "47.7993", "22.8625"),
    NEW_YORK("New York", "40.7143", "-74.006"),
    LONDON("London", "51.5085", "-0.1257"),
    CHISINAU("Chișinău", "47.0056", "28.8575"),
    HELSINKI("Helsinki", "60.3172", "24.9633"),
    COPENHAGEN("Copenhagen", "55.6759", "12.5655"),
    BRATISLAVA("Bratislava", "48.1482", "17.1067"),
    BUDAPEST("Budapest", "47.4984", "19.0404"),
    BELGRADE("Belgrad", "45.2167", "14.7333"),
    ATHENS("Athens", "37.9838", "23.7278"),
    SAN_MARINO("San Marino", "43.9367", "12.4464"),
    PARIS("Paris", "48.8534", "2.3488");

    private final String displayName;
    private final String lat;
    private final String lng;

    City(String displayName, String lat, String lng) {
        this.displayName = displayName;
        this.lat = lat;
        this.lng = lng;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
