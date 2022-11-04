package uk.gov.dwp.uc.pairtest.logging;

public enum LogEvent {

   PAYMENT("Transaction successful - Account: %s Payment: %s "),
   RESERVE_SEATS("Reserved successful - Account %s Seats: %s ");


   public final String message;

   LogEvent(String message) {
      this.message = message;
   }

   public String getMessage() {
      return message;
   }

}
