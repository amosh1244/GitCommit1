package com.RedBus.Controller;

import com.RedBus.BusOperator.PayLoad.BusOperatorDto;
import com.RedBus.Service.BusOperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bus-operator")
public class BusOperatorController {

    private BusOperatorService busOperatorService;

    public BusOperatorController(BusOperatorService busOperatorService) {
        this.busOperatorService = busOperatorService;
    }

    //http://localhost:8080/api/bus-operator
    @PostMapping
    public ResponseEntity<BusOperatorDto> scheduleBus(@RequestBody BusOperatorDto busOperatorDto){
        BusOperatorDto dto = busOperatorService.schedulesBus(busOperatorDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
