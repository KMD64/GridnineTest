package com.gridnine.testing;

import java.util.*;
import java.util.stream.Collectors;
public class FlightFilter {
    private Map<Class<? extends IRule>,IRule> rules;
    private boolean isBlacklist;
    private Collection<Flight> flights;
    public FlightFilter(List<Flight> flights,boolean isBlacklist){
        this.isBlacklist = isBlacklist;
        this.flights = Objects.requireNonNull(flights);
        this.rules = new HashMap<>();
    }
    public void setFlights(List<Flight> flights){
        flights = Objects.requireNonNull(flights);
    }
    public void setBlacklistMode(boolean isBlacklist){
        this.isBlacklist = isBlacklist;
    }

    public void addRule(IRule rule){
        rules.put(rule.getClass(),Objects.requireNonNull(rule));
    }
    public void resetRules(){
        rules.clear();
    }
    public List<Flight> filter(){
        return flights.parallelStream().filter(
                flight -> isBlacklist ^ rules.values().stream().reduce(
                        false,(result,rule)->result || rule.test(flight),Boolean::logicalOr)).collect(Collectors.toList());
    }
    public Map<IRule,List<Flight>> exclusions(){
        //Creating map with excluded flights by rules
        Map<IRule,List<Flight>> ex = new HashMap<>();
        rules.values().forEach(
                rule->ex.put(rule,flights.stream().filter(flight -> !isBlacklist ^ rule.test(flight)).collect(Collectors.toList())));
        return ex;
    }

}
