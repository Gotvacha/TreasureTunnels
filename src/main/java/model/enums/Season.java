package model.enums;

public enum Season {
    SPRING,
    SUMMER,
    FALL,
    WINTER;

    public Season moveToNextSeason() {
        Season[] seasons = Season.values();
        int currentIndex = this.ordinal();
        if (currentIndex < seasons.length - 1) {
            return seasons[currentIndex + 1];
        } else {
            return seasons[0];
        }
    }
}
