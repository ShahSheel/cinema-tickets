package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.List;

public class TicketServiceValidator {

    private static  final int MAXIMUM_PURCHASABLE_TICKETS = 20;


    public static void isValidTickets(Long accountId, List<TicketTypeRequest> ticketTypeRequestList) {

        purchaseLimit(ticketTypeRequestList);
        ticketRequirements(ticketTypeRequestList);
        accountID(accountId);

    }

    private static void purchaseLimit(List<TicketTypeRequest> ticketTypeRequestList){

        if(ticketTypeRequestList.stream().mapToInt(TicketTypeRequest::getNoOfTickets).sum() >= MAXIMUM_PURCHASABLE_TICKETS ) {
            throw new InvalidPurchaseException("Maximum purchasable tickets is " + MAXIMUM_PURCHASABLE_TICKETS);
        }
    }

    private static void accountID(Long accountId){

        if(accountId <= 0) {
            throw new InvalidPurchaseException("Account number cannot be negative");
        }
    }


    private static void ticketRequirements(List<TicketTypeRequest> ticketTypeRequestList){

//       Can be used if requirements will not change, otherwise a switch case will be easier to maintain
        if(ticketTypeRequestList.stream().noneMatch(ticketTypeRequest ->
                ticketTypeRequest.getTicketType().equals(TicketTypeRequest.Type.ADULT))){
            throw new InvalidPurchaseException("Adult is mandatory");

        }
    }


}
