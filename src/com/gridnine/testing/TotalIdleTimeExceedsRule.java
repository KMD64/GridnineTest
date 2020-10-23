package com.gridnine.testing;


import java.time.Duration;

import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TotalIdleTimeExceedsRule implements IRule {

    private final Duration limit;
    TotalIdleTimeExceedsRule(Duration limit){
        this.limit = Objects.requireNonNull(limit);
    }

    @Override
    public boolean test(Flight flight) {
        List<Segment> segments = Objects.requireNonNull(flight).getSegments();
        if(segments.size()<2)return false;
        Segment prev = null,cur = null;

        Duration idleTimeRemainder = Duration.from(limit);
        for(Iterator<Segment> it = segments.iterator();it.hasNext()&&!idleTimeRemainder.isNegative();){
            cur = it.next();
            if(prev == null){
                prev = cur;
                continue;
            }

            idleTimeRemainder = idleTimeRemainder.minus(Duration.between(prev.getArrivalDate(),cur.getDepartureDate()));
            prev = cur;
        }
        return idleTimeRemainder.isNegative();
    }

    @Override
    public String toString() {
        return "TotalIdleTimeExceedsRule{" +
                "limit=" + limit +
                '}';
    }
}
