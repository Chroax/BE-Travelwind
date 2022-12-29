package com.binar.finalproject.BEFlightTicket.service.impl;

import com.binar.finalproject.BEFlightTicket.dto.RouteResponse;
import com.binar.finalproject.BEFlightTicket.dummy.AirportDummy;
import com.binar.finalproject.BEFlightTicket.dummy.CityDummy;
import com.binar.finalproject.BEFlightTicket.dummy.RouteDummy;
import com.binar.finalproject.BEFlightTicket.dummy.TerminalDummy;
import com.binar.finalproject.BEFlightTicket.dto.RouteRequest;
import com.binar.finalproject.BEFlightTicket.model.Routes;
import com.binar.finalproject.BEFlightTicket.repository.AirportsRepository;
import com.binar.finalproject.BEFlightTicket.repository.CitiesRepository;
import com.binar.finalproject.BEFlightTicket.repository.RouteRepository;
import com.binar.finalproject.BEFlightTicket.repository.TerminalsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class RouteServiceImplTest {

    @InjectMocks
    RouteServiceImpl routeService;

    @Mock
    RouteRepository routeRepository;

    @Mock
    CitiesRepository citiesRepository;

    @Mock
    AirportsRepository airportsRepository;

    @Mock
    TerminalsRepository terminalsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test Add Route")
    void testAddRoute() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureCity("semarang");
        routeRequest.setArrivalCity("bali");
        routeRequest.setDepartureAirport("ahmad yani");
        routeRequest.setArrivalAirport("ngurah rai");
        routeRequest.setDepartureTerminal("A1");
        routeRequest.setArrivalTerminal("B1");


        Mockito.when(airportsRepository.findByAirportName(routeRequest.getDepartureAirport())).thenReturn(AirportDummy.getAirport().get(0));
        Mockito.when(airportsRepository.findByAirportName(routeRequest.getArrivalAirport())).thenReturn(AirportDummy.getAirport().get(1));
        Mockito.when(citiesRepository.findByCityName(routeRequest.getDepartureCity())).thenReturn(CityDummy.getCities().get(0));
        Mockito.when(citiesRepository.findByCityName(routeRequest.getArrivalCity())).thenReturn(CityDummy.getCities().get(1));
        Mockito.when(terminalsRepository.findTerminalExist(routeRequest.getDepartureTerminal(),
                AirportDummy.getAirport().get(0).getIataCode())).
                thenReturn(TerminalDummy.getTerminals().get(0));
        Mockito.when(terminalsRepository.findTerminalExist(routeRequest.getArrivalTerminal(),
                AirportDummy.getAirport().get(1).getIataCode())).
                thenReturn(TerminalDummy.getTerminals().get(1));
        Mockito.when(routeRepository.save(routeRequest.toRoutes())).thenReturn(routeRequest.toRoutes());

        var actualValue = routeService.addRoute(routeRequest);
        var expectedDepartureCity = "semarang";
        var expectedDepartureAirport = "ahmad yani";
        var expectedDepartureTerminal = "A1";
        var expectedArrivalCity = "bali";
        var expectedArrivalAirport = "ngurah rai";
        var expectedArrivalTerminal = "B1";

        Assertions.assertEquals(expectedDepartureCity, actualValue.getDepartureCity());
        Assertions.assertEquals(expectedDepartureAirport, actualValue.getDepartureAirport());
        Assertions.assertEquals(expectedDepartureTerminal, actualValue.getDepartureTerminal());
        Assertions.assertEquals(expectedArrivalCity, actualValue.getArrivalCity());
        Assertions.assertEquals(expectedArrivalAirport, actualValue.getArrivalAirport());
        Assertions.assertEquals(expectedArrivalTerminal, actualValue.getArrivalTerminal());
    }

    @Test
    @DisplayName("Test Add Route, Negative")
    void testAddRouteNotFound() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureCity("semarang");
        routeRequest.setArrivalCity("bali");
        routeRequest.setDepartureAirport("ahmad yani");
        routeRequest.setArrivalAirport("ngurah rai");
        routeRequest.setDepartureTerminal("A1");
        routeRequest.setArrivalTerminal("B1");

        UUID routeId = UUID.randomUUID();

        Mockito.when(routeRepository.findById(routeId)).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> {
            routeService.addRoute(routeRequest);
        });

    }

    @Test
    @DisplayName("Test Find Route By Departure City And Arrival City")
    void testFindByDepartureCityAndArrivalCity() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureCity("semarang");
        routeRequest.setArrivalCity("bali");
        routeRequest.setDepartureAirport("ahmad yani");
        routeRequest.setArrivalAirport("ngurah rai");
        routeRequest.setDepartureTerminal("A1");
        routeRequest.setArrivalTerminal("B1");

        Routes route = Routes.builder()
                .departureCity("semarang")
                .arrivalCity("bali")
                .departureAirport("ahmad yani")
                .arrivalAirport("ngurah rai")
                .departureTerminal("A1")
                .arrivalTerminal("B1")
                .build();

        List<Routes> routes =new ArrayList<>();
        routes.add(route);

        Mockito.when(routeRepository.findRouteByDepartureCityAndArrivalCity(routeRequest.getDepartureCity(), routeRequest.getArrivalCity()))
                .thenReturn(routes);

        List<RouteResponse> routeResponse = routeService.findByDepartureCityAndArrivalCity(routeRequest.getDepartureCity(), routeRequest.getArrivalCity());

        Assertions.assertEquals(1, routeResponse.size());
        Mockito.verify(routeRepository).findRouteByDepartureCityAndArrivalCity(route.getDepartureCity(), route.getArrivalCity());

    }

    @Test
    @DisplayName("Test Find Route By Departure Airport And Arrival Airport")
    void TestFindByDepartureAndArrivalAirport() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureCity("semarang");
        routeRequest.setArrivalCity("bali");
        routeRequest.setDepartureAirport("ahmad yani");
        routeRequest.setArrivalAirport("ngurah rai");
        routeRequest.setDepartureTerminal("A1");
        routeRequest.setArrivalTerminal("B1");

        Routes route = Routes.builder()
                .departureCity("semarang")
                .arrivalCity("bali")
                .departureAirport("ahmad yani")
                .arrivalAirport("ngurah rai")
                .departureTerminal("A1")
                .arrivalTerminal("B1")
                .build();

        List<Routes> routes =new ArrayList<>();
        routes.add(route);

        Mockito.when(routeRepository.findRouteByDepartureAndArrivalAirport(routeRequest.getDepartureAirport(), routeRequest.getArrivalAirport()))
                .thenReturn(routes);

        List<RouteResponse> routeResponse = routeService.findByDepartureAndArrivalAirport(routeRequest.getDepartureAirport(), routeRequest.getArrivalAirport());

        Assertions.assertEquals(1, routeResponse.size());
        Mockito.verify(routeRepository).findRouteByDepartureAndArrivalAirport(route.getDepartureAirport(), route.getArrivalAirport());
    }

    @Test
    @DisplayName("Test Get All Route")
    void testGetAllRoute() {
        List<Routes> allRoutes = RouteDummy.getAllRoutes();
        Mockito.when(routeRepository.findAll()).thenReturn(allRoutes);

        var actualValue = routeService.getAllRoute();
        var expectedSize =  1;

        Assertions.assertEquals(expectedSize, actualValue.size());
    }

    @Test
    @DisplayName("Test Update Route")
    void testUpdateRoute() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureCity("semarang");
        routeRequest.setArrivalCity("bali");
        routeRequest.setDepartureAirport("ahmad yani");
        routeRequest.setArrivalAirport("ngurah rai");
        routeRequest.setDepartureTerminal("A1");
        routeRequest.setArrivalTerminal("B1");

        Routes routes = routeRequest.toRoutes();
        UUID routeId = UUID.randomUUID();

        Mockito.when(routeRepository.findById(routeId)).thenReturn(Optional.of(routes));
        Mockito.when(airportsRepository.findByAirportName(routeRequest.getDepartureAirport())).thenReturn(AirportDummy.getAirport().get(0));
        Mockito.when(airportsRepository.findByAirportName(routeRequest.getArrivalAirport())).thenReturn(AirportDummy.getAirport().get(1));
        Mockito.when(citiesRepository.findByCityName(routeRequest.getDepartureCity())).thenReturn(CityDummy.getCities().get(0));
        Mockito.when(citiesRepository.findByCityName(routeRequest.getArrivalCity())).thenReturn(CityDummy.getCities().get(1));
        Mockito.when(terminalsRepository.findByTerminalName(routeRequest.getDepartureTerminal())).
                thenReturn(TerminalDummy.getTerminals().get(0));
        Mockito.when(terminalsRepository.findByTerminalName(routeRequest.getArrivalTerminal())).
                thenReturn(TerminalDummy.getTerminals().get(1));
        Mockito.when(routeRepository.save(routeRequest.toRoutes())).thenReturn(routeRequest.toRoutes());

        var actualValue = routeService.updateRoute(routeRequest, routeId);
        var expectedDepartureCity = "semarang";
        var expectedDepartureAirport = "ahmad yani";
        var expectedDepartureTerminal = "A1";
        var expectedArrivalCity = "bali";
        var expectedArrivalAirport = "ngurah rai";
        var expectedArrivalTerminal = "B1";

        Assertions.assertEquals(expectedDepartureCity, actualValue.getDepartureCity());
        Assertions.assertEquals(expectedDepartureAirport, actualValue.getDepartureAirport());
        Assertions.assertEquals(expectedDepartureTerminal, actualValue.getDepartureTerminal());
        Assertions.assertEquals(expectedArrivalCity, actualValue.getArrivalCity());
        Assertions.assertEquals(expectedArrivalAirport, actualValue.getArrivalAirport());
        Assertions.assertEquals(expectedArrivalTerminal, actualValue.getArrivalTerminal());
    }

    @Test
    @DisplayName("Delete Routes")
    void testDeleteRoute() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureCity("semarang");
        routeRequest.setArrivalCity("bali");
        routeRequest.setDepartureAirport("ahmad yani");
        routeRequest.setArrivalAirport("ngurah rai");
        routeRequest.setDepartureTerminal("A1");
        routeRequest.setArrivalTerminal("B1");
        Routes routes =  routeRequest.toRoutes();
        UUID routeId = UUID.randomUUID();

        Mockito.when(routeRepository.findById(routeId)).thenReturn(Optional.of(routes));
        Mockito.doNothing().when(routeRepository).deleteById(routeId);

        var actualValue = routeService.deleteRoute(routeId);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);

    }
}
