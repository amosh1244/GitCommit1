package com.RedBus.BusOperator.Repository;

import com.RedBus.BusOperator.Entity.TicketCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCostRepository extends JpaRepository<TicketCost, String> {
}
