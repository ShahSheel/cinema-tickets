package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.TicketServiceValidator;

import java.util.List;
import java.util.logging.Logger;

import static uk.gov.dwp.uc.pairtest.logging.LogEvent.*;

public class TicketServiceImpl implements TicketService {

    private static final Logger LOG = Logger.getLogger(TicketServiceImpl.class.getName());
    private static final int UNRESERVE_SEAT = 0;

    private int totalCost;
    private int reserveSeats;

    private TicketPaymentService ticketPaymentService;
    private SeatReservationService seatReservationService;


    public TicketServiceImpl
    (
            TicketPaymentService ticketPaymentService,
            SeatReservationService seatReservationService
    )
    {
        this.ticketPaymentService = ticketPaymentService;
        this.seatReservationService = seatReservationService;
    }

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

       List<TicketTypeRequest> ticketTypeRequestList = List.of(ticketTypeRequests);
       TicketServiceValidator.isValidTickets(accountId, ticketTypeRequestList);


       for (TicketTypeRequest ticketTypeRequest : ticketTypeRequests) {

           this.totalCost += ticketTypeRequest.getTicketValue() * ticketTypeRequest.getNoOfTickets();

           this.reserveSeats += this.seatsToReserve( ticketTypeRequest );
       }

       ticketPaymentService.makePayment(accountId, this.totalCost);
       //Log payment
       LOG.info(String.format(PAYMENT.getMessage(), accountId, this.totalCost));

       seatReservationService.reserveSeat(accountId, this.reserveSeats);
       // Log Reservation
       LOG.info(String.format(RESERVE_SEATS.getMessage(), accountId, this.reserveSeats));

    }

    private int seatsToReserve(TicketTypeRequest ticketTypeRequest){

        switch( ticketTypeRequest.getTicketType() ){
            case ADULT:
            case CHILD: return ticketTypeRequest.getNoOfTickets();
            case INFANT: return UNRESERVE_SEAT;
        }

        return 0;

    }
}
