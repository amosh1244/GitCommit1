package com.RedBus.User.Service;

import com.RedBus.BusOperator.Entity.BusOperator;
import com.RedBus.BusOperator.Repository.BusOperatorRepository;
import com.RedBus.User.PayLoad.BusListDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchBusesService {

    private BusOperatorRepository busOperatorRepository;

    public SearchBusesService(BusOperatorRepository busOperatorRepository) {
        this.busOperatorRepository = busOperatorRepository;
    }


    public List<BusListDto> searchBusBy(String departureCity, String arrivalCity, Date departureDate){
        List<BusOperator> busesAvailable = busOperatorRepository.findByDepartureCityAndArrivalCityAndDepartureDate(departureCity, arrivalCity, departureDate);
        List<BusListDto> dtos = busesAvailable.stream().map(bus -> mapToDto(bus)).collect(Collectors.toList());
        return dtos;
    }

    BusListDto mapToDto(BusOperator busOperator){
        BusListDto busListdto = new BusListDto();
        busListdto.setBusId(busOperator.getBusId());
        busListdto.setBusNumber(busOperator.getBusNumber());
        busListdto.setBusType(busOperator.getBusType());
        busListdto.setDepartureDate(busOperator.getDepartureDate());
        busListdto.setArrivalDate(busOperator.getArrivalDate());
        busListdto.setDepartureTime(busOperator.getDepartureTime());
        busListdto.setArrivalTime(busOperator.getArrivalTime());
        busListdto.setNumberSeats(busOperator.getNumberSeats());
        busListdto.setAmenities(busOperator.getAmenities());
        busListdto.setDepartureCity(busOperator.getDepartureCity());
        busListdto.setArrivalCity(busOperator.getArrivalCity());
        busListdto.setBusOperatorCompanyName(busOperator.getBusOperatorCompanyName());

        return busListdto;

    }

}
