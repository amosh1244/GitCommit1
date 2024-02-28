package com.RedBus.Service;

import com.RedBus.BusOperator.Entity.BusOperator;
import com.RedBus.BusOperator.Entity.TicketCost;
import com.RedBus.BusOperator.PayLoad.BusOperatorDto;
import com.RedBus.BusOperator.Repository.BusOperatorRepository;
import com.RedBus.BusOperator.Repository.TicketCostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusOperatorServiceImpl implements BusOperatorService {

    private BusOperatorRepository busOperatorRepository;

    private ModelMapper mapper;

    private TicketCostRepository ticketCostRepository;


    public BusOperatorServiceImpl(BusOperatorRepository busOperatorRepository,
                                  ModelMapper mapper,
                                  TicketCostRepository ticketCostRepository) {

        this.busOperatorRepository = busOperatorRepository;
        this.mapper = mapper;
        this.ticketCostRepository = ticketCostRepository;
    }

    @Override
    public BusOperatorDto schedulesBus(BusOperatorDto busOperatorDto) {
        BusOperator busOperator = mapToEntity(busOperatorDto);

        TicketCost ticketCost= new TicketCost();
        ticketCost.setTicketId(busOperatorDto.getTicketCost().getTicketId());
        ticketCost.setCost(busOperatorDto.getTicketCost().getCost());
        ticketCost.setCode(busOperatorDto.getTicketCost().getCode());
        ticketCost.setDiscountAmount(busOperatorDto.getTicketCost().getDiscountAmount());

        busOperator.setTicketCost(ticketCost);

        String busId = UUID.randomUUID().toString();
        busOperator.setBusId(busId);

        BusOperator savedBusSchedule = busOperatorRepository.save(busOperator);

        return mapToDto(savedBusSchedule);
    }

    BusOperator mapToEntity(BusOperatorDto busOperatorDto){
        BusOperator busOperator = mapper.map(busOperatorDto, BusOperator.class);
        return busOperator;
    }

    BusOperatorDto mapToDto(BusOperator busOperator){
        BusOperatorDto busOperatorDto = mapper.map(busOperator, BusOperatorDto.class);
        return  busOperatorDto;
    }

}
