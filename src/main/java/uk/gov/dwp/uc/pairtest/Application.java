package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.validation.TicketServiceValidator;

public class Application {

    public static void main(String[] args) {

        TicketPaymentService ticketPaymentService = new TicketPaymentService() {
            @Override
            public void makePayment(long accountId, int totalAmountToPay) {

            }
        };
        SeatReservationService seatReservationService = new SeatReservationService() {
            @Override
            public void reserveSeat(long accountId, int totalSeatsToAllocate) {

            }
        };


        TicketServiceImpl ticketServiceImpl = new TicketServiceImpl(ticketPaymentService,seatReservationService);
        TicketTypeRequest adult = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest child = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);
        TicketTypeRequest infant = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);


        ticketServiceImpl.purchaseTickets(12345L, adult, child, infant);
    }
}
