package com.gridnine.testing;

import org.w3c.dom.ls.LSOutput;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("Before filtering:");
        flights.forEach(System.out::println);
        System.out.println("--------------------------------");
        FlightFilter filter = new FlightFilter(flights,true);
        filter.addRule(new HasInvalidSegmentsRule());
        filter.addRule(new TotalIdleTimeExceedsRule(Duration.ofHours(2)));
        filter.addRule(new IsDepartBeforeRule(LocalDateTime.now()));

        System.out.println("After filtering:");
        filter.filter().forEach(System.out::println);
        System.out.println("--------------------------------");
        System.out.println("Exclusions:");
        for(Map.Entry<IRule, List<Flight>> pair:filter.exclusions().entrySet()){
            System.out.println(pair.getKey());
            pair.getValue().forEach(System.out::println);
            System.out.println("--------------------------------");
        }
    }
}
