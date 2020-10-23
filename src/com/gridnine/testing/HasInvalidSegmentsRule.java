package com.gridnine.testing;

import java.util.Objects;

public class HasInvalidSegmentsRule implements IRule {

    @Override
    public boolean test(Flight flight) {
        return Objects.requireNonNull(flight).getSegments().parallelStream().anyMatch(n->n.getDepartureDate().isAfter(n.getArrivalDate()));
    }

    @Override
    public String toString() {
        return "HasInvalidSegmentsRule";
    }
}
