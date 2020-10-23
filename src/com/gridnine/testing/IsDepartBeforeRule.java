package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.Objects;

public class IsDepartBeforeRule implements IRule{
    public IsDepartBeforeRule(LocalDateTime dt){
        this.dt = dt;
    }
    private LocalDateTime dt;
    @Override
    public boolean test(Flight flight) {
        return Objects.requireNonNull(flight).getSegments().stream().anyMatch(n->n.getDepartureDate().isBefore(dt));
    }

    @Override
    public String toString() {
        return "HasDepartBeforeRule{" +
                "dt=" + dt +
                '}';
    }
}
