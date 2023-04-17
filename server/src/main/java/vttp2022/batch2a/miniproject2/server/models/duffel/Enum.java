package vttp2022.batch2a.miniproject2.server.models.duffel;

public class Enum {

  public enum CabinClass {
    first, business, premium_economy, economy
  }

  public enum PassengerType {
    adult, child, infant_without_seat
  }

  public enum FareType {
    accompanying_adult, contract_bulk, contract_bulk_child, 
    contract_bulk_infant_with_seat, contract_bulk_infant_without_seat, 
    frequent_flyer, group_inclusive_tour, group_inclusive_tour_child, 
    humanitarian, individual_inclusive_tour_child, marine, seat_only, 
    student, teacher, tour_operator_inclusive, tour_operator_inclusive_infant, 
    unaccompanied_child, visiting_friends_and_family
  }

  public enum BaggageType {
    checked, carry_on
  }
  
}
