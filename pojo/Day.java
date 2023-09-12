package pojo;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

// ----------------- PURPOSE: Defining & validating (work) day data -----------------

public class Day {
    private int dayID;
    //private String dayName;
    private DayOfWeek dayName;
    private LocalTime openingTime;
    private LocalTime closingTime;

    // TODO argument validation

    public Day(int dayID, DayOfWeek dayName, LocalTime openingTime, LocalTime closingTime) {
        setDayID(dayID);
        setDayName(dayName);
        setOpeningTime(openingTime);
        setClosingTime(closingTime);
    }

    // GETTERS & SETTERS
    public int getDayID() {
        return this.dayID;
    }
    public void setDayID(int dayID) {
        this.dayID = dayID;
    }
    public DayOfWeek getDayName() {
        return this.dayName;
    }
    public void setDayName(DayOfWeek dayName) {
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

    @Override
    public Day clone() {
        return new Day(dayID, dayName, openingTime, closingTime);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Day)) {
            return false;
        }
        Day day = (Day) o;
        return dayID == day.dayID && Objects.equals(dayName, day.dayName) && Objects.equals(openingTime, day.openingTime) && Objects.equals(closingTime, day.closingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayID, dayName, openingTime, closingTime);
    }



}
