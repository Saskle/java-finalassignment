package pojo;

import java.time.LocalTime;

public class Day {
    private int dayID;
    private String dayName;
    private LocalTime openingTime;
    private LocalTime closingTime;

    // TODO argument validation

    public Day(int dayID, String dayName, LocalTime openingTime, LocalTime closingTime) {
        setDayID(dayID);
        setDayName(dayName);
        setOpeningTime(openingTime);
        setClosingTime(closingTime);
    }

    public int getDayID() {
        return this.dayID;
    }
    public void setDayID(int dayID) {
        this.dayID = dayID;
    }
    public String getDayName() {
        return this.dayName;
    }
    public void setDayName(String dayName) {
        this.dayName = dayName;
    }
    public LocalTime getOpeningTime() {
        return this.openingTime;
    }
    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }
    public LocalTime getClosingTime() {
        return this.closingTime;
    }
    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }


    @Override
    public String toString() {
        return getDayName() + ": " + getOpeningTime() + 
            " - " + getClosingTime();
    }



}
