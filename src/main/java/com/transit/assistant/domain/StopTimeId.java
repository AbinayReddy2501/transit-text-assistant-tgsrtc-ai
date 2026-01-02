package com.transit.assistant.domain;

import java.io.Serializable;
import java.util.Objects;

public class StopTimeId implements Serializable {

    private Long tripId;
    private Integer stopSequence;

    public StopTimeId() {}

    public StopTimeId(Long tripId, Integer stopSequence) {
        this.tripId = tripId;
        this.stopSequence = stopSequence;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Integer getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(Integer stopSequence) {
        this.stopSequence = stopSequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopTimeId that = (StopTimeId) o;
        return Objects.equals(tripId, that.tripId) && Objects.equals(stopSequence, that.stopSequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, stopSequence);
    }
}


