package uk.gov.dwp.uc.pairtest;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.TicketServiceValidator;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest {

    @Mock
    private TicketPaymentService ticketPaymentService;

    @Mock
    private SeatReservationService seatReservationService;

    @Mock
    private TicketServiceValidator ticketServiceValidator;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private static final long ACCOUNT_ID = 12345L;


    @Test
    public void one_adult_ticket_should_cost_twenty_with_one_seat() {

        TicketTypeRequest adult = new TicketTypeRequest(Type.ADULT, 1);

        ticketService.purchaseTickets(ACCOUNT_ID, adult);

        verify(ticketPaymentService, times(1)).makePayment(ACCOUNT_ID, 20);
        verify(seatReservationService, times(1)).reserveSeat(ACCOUNT_ID, 1);
    }

    @Test
    public void one_adult_ticket_and_one_child_ticket_should_request_two_seats_with_cost_30() {

        TicketTypeRequest adult = new TicketTypeRequest(Type.ADULT, 1);
        TicketTypeRequest child = new TicketTypeRequest(Type.CHILD, 1);

        ticketService.purchaseTickets(ACCOUNT_ID, adult, child);

        verify(ticketPaymentService, times(1)).makePayment(ACCOUNT_ID, 30);
        verify(seatReservationService, times(1)).reserveSeat(ACCOUNT_ID, 2);
    }

    @Test
    public void one_adult_ticket_and_one_child_ticket_and_one_infant_ticket_should_request_two_seats_with_cost_30() {

        TicketTypeRequest adult = new TicketTypeRequest(Type.ADULT, 1);
        TicketTypeRequest child = new TicketTypeRequest(Type.CHILD, 1);
        TicketTypeRequest infant = new TicketTypeRequest(Type.INFANT, 1);

        ticketService.purchaseTickets(ACCOUNT_ID, adult, child, infant);

        verify(ticketPaymentService, times(1)).makePayment(ACCOUNT_ID, 30);
        verify(seatReservationService, times(1)).reserveSeat(ACCOUNT_ID, 2);
    }

    @Test
    public void multiple_ticket_with_correct_seats_and_cost() {

        TicketTypeRequest adult = new TicketTypeRequest(Type.ADULT, 6);
        TicketTypeRequest child = new TicketTypeRequest(Type.CHILD, 9);
        TicketTypeRequest infant = new TicketTypeRequest(Type.INFANT, 2);

        ticketService.purchaseTickets(ACCOUNT_ID, adult, child, infant);

        verify(ticketPaymentService, times(1)).makePayment(ACCOUNT_ID, 210);
        verify(seatReservationService, times(1)).reserveSeat(ACCOUNT_ID, 15);
    }

    @Test (expected = InvalidPurchaseException.class)
    public void expect_exception_when_tickets_is_over_twenty() {

        TicketTypeRequest adult = new TicketTypeRequest(Type.ADULT, 21);
        TicketTypeRequest child = new TicketTypeRequest(Type.INFANT, 1);

        ticketService.purchaseTickets(ACCOUNT_ID, adult, child );

        verify(ticketPaymentService, times(1)).makePayment(ACCOUNT_ID, 430);
        verify(seatReservationService, times(1)).reserveSeat(ACCOUNT_ID, 21);
    }

    @Test (expected = InvalidPurchaseException.class)
    public void expect_exception_when_no_adult_is_requesting() {

        TicketTypeRequest child = new TicketTypeRequest(Type.CHILD, 2);
        TicketTypeRequest infant = new TicketTypeRequest(Type.INFANT, 1);

        ticketService.purchaseTickets(ACCOUNT_ID, child, infant);

        verify(ticketPaymentService, times(1)).makePayment(ACCOUNT_ID, 50);
        verify(seatReservationService, times(1)).reserveSeat(ACCOUNT_ID, 3);
    }

    @Test (expected = InvalidPurchaseException.class)
    public void expect_exception_invalid_account_id() {

        TicketTypeRequest adult = new TicketTypeRequest(Type.ADULT, 3);
        TicketTypeRequest child = new TicketTypeRequest(Type.CHILD, 2);
        TicketTypeRequest infant = new TicketTypeRequest(Type.INFANT, 1);

        ticketService.purchaseTickets(-1L, child, infant);

        verify(ticketPaymentService, times(1)).makePayment(ACCOUNT_ID, 80);
        verify(seatReservationService, times(1)).reserveSeat(ACCOUNT_ID, 5);
    }


}
