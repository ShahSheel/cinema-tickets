package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public class Application {

    public static void main(String[] args) {


        TicketServiceImpl ticketServiceImpl = new TicketServiceImpl();
        TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest ticketTypeRequest2 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest ticketTypeRequest3 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);

        ticketServiceImpl.purchaseTickets(12345L, ticketTypeRequest, ticketTypeRequest2);
    }
}
