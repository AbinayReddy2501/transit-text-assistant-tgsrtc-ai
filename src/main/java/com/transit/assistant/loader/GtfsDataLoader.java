package com.transit.assistant.loader;

import com.transit.assistant.domain.*;
import com.transit.assistant.repository.*;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class GtfsDataLoader {

    private final StopRepository stopRepository;
    private final RouteRepository routeRepository;
    private final TripRepository tripRepository;
    private final AgencyRepository agencyRepository;
    private final ServiceCalendarRepository serviceCalendarRepository;
    private final StopTimeRepository stopTimeRepository;

    private final List<Stop> stops = new ArrayList<>();
    private final List<Route> routes = new ArrayList<>();
    private final List<Trip> trips = new ArrayList<>();

    public GtfsDataLoader(StopRepository stopRepository, RouteRepository routeRepository,
                          TripRepository tripRepository, AgencyRepository agencyRepository,
                          ServiceCalendarRepository serviceCalendarRepository,
                          StopTimeRepository stopTimeRepository) {
        this.stopRepository = stopRepository;
        this.routeRepository = routeRepository;
        this.tripRepository = tripRepository;
        this.agencyRepository = agencyRepository;
        this.serviceCalendarRepository = serviceCalendarRepository;
        this.stopTimeRepository = stopTimeRepository;
    }

    @PostConstruct
    public void loadData() {
        loadAgency();
        loadStops();
        loadRoutes();
        loadTrips();
        loadServiceCalendar();
        loadStopTimes();
        System.out.println("Agency loaded: " + agencyRepository.count());
        System.out.println("Stops loaded: " + stops.size());
        System.out.println("Routes loaded: " + routes.size());
        System.out.println("Trips loaded: " + trips.size());
        System.out.println("Service Calendar loaded: " + serviceCalendarRepository.count());
        System.out.println("Stop Times loaded: " + stopTimeRepository.count());
    }

    private void loadAgency() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/agency.txt")))) {

            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens.length < 4) {
                    continue;
                }

                Agency agency = new Agency();
                agency.setAgencyId(tokens[0]);
                agency.setAgencyName(tokens[1]);
                agency.setAgencyUrl(tokens[2]);
                agency.setAgencyTimezone(tokens[3]);

                agencyRepository.save(agency);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
private void loadStops() {
    try (BufferedReader br = new BufferedReader(
            new InputStreamReader(
                    getClass().getResourceAsStream("/stops.txt")))) {

        String line = br.readLine(); // skip header

        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",");

            // Expecting exactly 5 columns
            if (tokens.length < 5) {
                continue;
            }

            Stop stop = new Stop();
            stop.setStopId(Long.parseLong(tokens[0]));
            stop.setStopName(tokens[1]);
            stop.setZoneId(Integer.parseInt(tokens[2]));
            stop.setStopLat(Double.parseDouble(tokens[3]));
            stop.setStopLon(Double.parseDouble(tokens[4]));

            stopRepository.save(stop);
            stops.add(stop);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void loadRoutes() {
    try (BufferedReader br = new BufferedReader(
            new InputStreamReader(
                    getClass().getResourceAsStream("/routes.txt")))) {

        String line = br.readLine(); // skip header

        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",");

            if (tokens.length < 4) {
                continue;
            }

            Route route = new Route();
            route.setRouteId(Long.parseLong(tokens[0]));
            route.setAgencyId(tokens[2]);
            route.setRouteShortName(tokens[3]);
            route.setRouteLongName(tokens[1]);

            routeRepository.save(route);
            routes.add(route);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void loadTrips() {
    try (BufferedReader br = new BufferedReader(
            new InputStreamReader(
                    getClass().getResourceAsStream("/trips.txt")))) {

        String line = br.readLine(); // skip header

        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",");

            if (tokens.length < 9) {
                continue;
            }

            Trip trip = new Trip();
            trip.setRouteId(Long.parseLong(tokens[0]));
            trip.setTripId(Long.parseLong(tokens[1]));
            trip.setServiceId(tokens[2]);
            trip.setDirectionId(Integer.parseInt(tokens[3]));
            trip.setPatternId(tokens[4]);
            trip.setTripHeadsign(tokens[5]);
            trip.setTripShortName(tokens[6]);
            trip.setDepot(tokens[7]);
            trip.setBusClass(tokens[8]);

            trips.add(trip);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    private void loadServiceCalendar() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/calendar.txt")))) {

            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens.length < 8) {
                    continue;
                }

                ServiceCalendar serviceCalendar = new ServiceCalendar();
                serviceCalendar.setServiceId(tokens[0]);
                serviceCalendar.setMonday(Integer.parseInt(tokens[1]));
                serviceCalendar.setTuesday(Integer.parseInt(tokens[2]));
                serviceCalendar.setWednesday(Integer.parseInt(tokens[3]));
                serviceCalendar.setThursday(Integer.parseInt(tokens[4]));
                serviceCalendar.setFriday(Integer.parseInt(tokens[5]));
                serviceCalendar.setSaturday(Integer.parseInt(tokens[6]));
                serviceCalendar.setSunday(Integer.parseInt(tokens[7]));

                serviceCalendarRepository.save(serviceCalendar);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStopTimes() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/stop_times.txt")))) {

            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens.length < 5) {
                    continue;
                }

                StopTime stopTime = new StopTime();
                stopTime.setTripId(Long.parseLong(tokens[0]));
                stopTime.setStopSequence(Integer.parseInt(tokens[1]));
                stopTime.setStopId(Long.parseLong(tokens[2]));
                stopTime.setArrivalTime(tokens[3]);
                stopTime.setDepartureTime(tokens[4]);

                stopTimeRepository.save(stopTime);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Stop> getStops() {
        return stops;
    }

}
