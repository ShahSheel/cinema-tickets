package uk.gov.dwp.uc.pairtest.validation;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TicketServiceValidatorTest{

    @Mock
    private TicketPaymentService ticketPaymentService;

    @Mock
    private SeatReservationService seatReservationService;

    @Mock
    private TicketServiceValidator ticketServiceValidator;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private static final long ACCOUNT_ID = 12345L;

    @Test (expected = InvalidPurchaseException.class)
    public void expect_exception_when_tickets_is_over_twenty() {

        TicketTypeRequest adult = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 21);
        TicketTypeRequest child = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        ticketService.purchaseTickets(ACCOUNT_ID, adult, child );

        verify(ticketPaymentService, times(1)).makePayment(ACCOUNT_ID, 60);
        verify(seatReservationService, times(1)).reserveSeat(ACCOUNT_ID, 4);
    }

    @Test (expected = InvalidPurchaseException.class)
    public void expect_exception_when_no_adult_is_requesting() {

        TicketTypeRequest child = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2);
        TicketTypeRequest infant = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        ticketService.purchaseTickets(ACCOUNT_ID, child, infant);

        verify(ticketPaymentService, times(1)).makePayment(ACCOUNT_ID, 2);
        verify(seatReservationService, times(1)).reserveSeat(ACCOUNT_ID, 4);
    }

}
