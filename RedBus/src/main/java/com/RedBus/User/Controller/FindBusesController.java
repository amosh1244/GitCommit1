package com.RedBus.User.Controller;

import com.RedBus.BusOperator.Entity.BusOperator;
import com.RedBus.User.PayLoad.BusListDto;
import com.RedBus.User.Service.SearchBusesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class FindBusesController {

    private SearchBusesService searchBusesService;

    public FindBusesController(SearchBusesService searchBusesService) {
        this.searchBusesService = searchBusesService;
    }

    //http://localhost:8080/api/user/searchBuses?departureCity=CityA&arrivalCity=CityB&departureDate=2023-01-01
    @GetMapping("/searchBuses")
    public List<BusListDto> searchBuses(@RequestParam ("departureCity") String departureCity,
                                         @RequestParam ("arrivalCity") String arrivalCity,
                                         @RequestParam ("departureDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date departureDate ){

        List<BusListDto> busListDtos = searchBusesService.searchBusBy(departureCity, arrivalCity, departureDate);

        return busListDtos;
    }

}