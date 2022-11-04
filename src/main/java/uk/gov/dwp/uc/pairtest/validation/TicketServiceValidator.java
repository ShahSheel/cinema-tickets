package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.List;

public class TicketServiceValidator {

    private static  final int MAXIMUM_PURCHASABLE_TICKETS = 20;


    public boolean isValidTickets(List<TicketTypeRequest> ticketTypeRequestList) {


       return this.purchaseLimit(ticketTypeRequestList).ticketRequirements(ticketTypeRequestList);


    }

    private TicketServiceValidator purchaseLimit(List<TicketTypeRequest> ticketTypeRequestList ){

        if(ticketTypeRequestList.stream().mapToInt(tickets -> tickets.getNoOfTickets()).sum() >= MAXIMUM_PURCHASABLE_TICKETS )
            throw new InvalidPurchaseException("Maximum purchasable tickets is " + MAXIMUM_PURCHASABLE_TICKETS );

        return this;
    }

    private boolean ticketRequirements(List<TicketTypeRequest> ticketTypeRequestList ){


        for(TicketTypeRequest ticketTypeRequest : ticketTypeRequestList){

            switch (ticketTypeRequest.getTicketType()) {
               case ADULT:
                    if(ticketTypeRequest.getNoOfTickets() <= 0 ){
                        throw new InvalidPurchaseException("Adult ticket is mandatory");
                    }
                    break;

               case CHILD:
                case INFANT:
                    break;
            }
        }
        return true;
    }


}
