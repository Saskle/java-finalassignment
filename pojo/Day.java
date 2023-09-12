package pojo;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

// ----------------- PURPOSE: Defining & validating (work) day data -----------------

public class Day {
    private int id;
    private DayOfWeek dayName;
    private LocalTime openingTime;
    private LocalTime closingTime;

    // TODO argument validation

    public Day(int id, DayOfWeek dayName, LocalTime openingTime, LocalTime closingTime) {
        setId(id);
        setDayName(dayName);
        setOpeningTime(openingTime);
        setClosingTime(closingTime);
    }

    // GETTERS & SETTERS
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("A day's ID cannot be 0 or negative.");
        }
        this.id = id;
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
        return new Day(id, dayName, openingTime, closingTime);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Day)) {
            return false;
        }
        Day day = (Day) o;
        return id == day.id && Objects.equals(dayName, day.dayName) && Objects.equals(openingTime, day.openingTime) && Objects.equals(closingTime, day.closingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dayName, openingTime, closingTime);
    }



}
