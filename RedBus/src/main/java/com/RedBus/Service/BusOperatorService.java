package com.RedBus.Service;

import com.RedBus.BusOperator.PayLoad.BusOperatorDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface BusOperatorService {

    BusOperatorDto schedulesBus(@RequestBody BusOperatorDto busOperatorDto);

}
