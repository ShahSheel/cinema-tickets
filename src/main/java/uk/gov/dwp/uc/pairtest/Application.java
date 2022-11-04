package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.validation.TicketServiceValidator;

public class Application {

    public static void main(String[] args) {

        TicketPaymentService ticketPaymentService = (accountId, totalAmountToPay) -> {};
        SeatReservationService seatReservationService = (accountId, totalSeatsToAllocate) -> {};


        TicketServiceImpl ticketServiceImpl = new TicketServiceImpl(ticketPaymentService,seatReservationService);
        TicketTypeRequest adult = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 6);
        TicketTypeRequest child = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 9);
        TicketTypeRequest infant = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2);

        ticketServiceImpl.purchaseTickets(-1L, adult, child, infant);
    }
}
