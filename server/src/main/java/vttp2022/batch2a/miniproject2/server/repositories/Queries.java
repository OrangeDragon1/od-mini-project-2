package vttp2022.batch2a.miniproject2.server.repositories;

public class Queries {

  public static final String SQL_FIND_USER_BY_EMAIL = """
    SELECT * FROM users WHERE email = ?
      """;
  public static final String SQL_FIND_USER_BY_VERIFICATION_STRING = """
    SELECT * FROM users WHERE verification_string = ?
      """;
  public static final String SQL_REGISTER = """
    INSERT INTO users(id, first_name, last_name, email, password, verification_string) 
    VALUES(UUID(), ?, ?, ? ,?, ?)
      """;
  public static final String UPDATE_USER_VERIFIED = """
    UPDATE users SET verified = 1 WHERE verification_string = ?
      """;
  public static final String DELETE_VERIFICATION_STRING = """
    UPDATE users SET verification_string = NULL WHERE verification_string = ?
      """;
  public static final String SQL_UPDATE_USER_PASSWORD = """
    UPDATE users SET password = ? WHERE email = ?
      """;
  public static final String INSERT_RESET_PWD_STRING = """
    UPDATE users SET reset_pwd_string = ? WHERE email = ?
      """;
  public static final String SQL_FIND_USER_BY_RESET_PWD_STRING = """
    SELECT * FROM users WHERE reset_pwd_string = ?
      """;
  public static final String SQL_RESET_PWD_BY_RESET_PWD_STRING = """
    UPDATE users SET password = ? WHERE reset_pwd_string = ?
      """;
  public static final String DELETE_RESET_PWD_STRING = """
    UPDATE users SET reset_pwd_string = NULL WHERE reset_pwd_string = ?
      """;
      
  public static final String INSERT_OWNER = """
    INSERT INTO owners(name, logo_symbol_url, logo_lockup_url, id, iata_code, conditions_of_carriage_url) 
    VALUES(?, ?, ?, ?, ?, ?)
      """;
  public static final String FIND_OWNER_BY_ID = """
    SELECT * FROM owners WHERE id = ?
      """;
  public static final String INSERT_ORDER = """
    INSERT INTO orders(total_currency, total_amount, tax_currency, tax_amount, owners_id, id, created_at, booking_reference, base_currency, base_amount, users_id) 
    VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
      """;
  public static final String FIND_ORDER_BY_ID = """
      SELECT * FROM orders WHERE id = ?
      """;
  public static final String INSERT_SLICE = """
    INSERT INTO slices(origin_name, origin_iata_country_code, origin_iata_code, id, fare_brand_name, duration, destination_name, destination_iata_country_code, destination_iata_code, orders_id) 
    VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
      """;
  public static final String INSERT_OPERATING_CARRIER = """
    INSERT INTO operating_carrier(name, logo_symbol_url, logo_lockup_url, id, iata_code, conditions_of_carriage_url) 
    VALUES(?, ?, ?, ?, ?, ?)
      """;
  public static final String FIND_OPERATING_CARRIER_BY_ID = """
    SELECT * FROM operating_carrier WHERE id = ?
      """;
  public static final String INSERT_MARKETING_CARRIER = """
    INSERT INTO marketing_carrier(name, logo_symbol_url, logo_lockup_url, id, iata_code, conditions_of_carriage_url) 
    VALUES(?, ?, ?, ?, ?, ?)
      """;
  public static final String FIND_MARKETING_CARRIER_BY_ID = """
    SELECT * FROM marketing_carrier WHERE id = ?
      """;
  public static final String INSERT_AIRCRAFT = """
    INSERT INTO aircraft(name, id, iata_code) VALUES(?, ?, ?)
      """;
  public static final String FIND_AIRCRAFT_BY_ID = """
    SELECT * FROM aircraft WHERE id = ?
      """;
  public static final String INSERT_SEGMENT = """
    INSERT INTO segments(origin_terminal, origin_name, origin_iata_country_code, origin_iata_code, operating_carrier_flight_number, operating_carrier_id, marketing_carrier_flight_number, marketing_carrier_id, id, duration, distance, destination_terminal, destination_name, destination_iata_country_code, destination_iata_code, departing_at, arriving_at, aircraft_id, slices_id) 
    VALUES(?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
      """;
  public static final String INSERT_SEGMENT_PASSENGER = """
    INSERT INTO segment_passengers(passenger_id, cabin_class_marketing_name, cabin_class, segments_id) 
    VALUES(?, ?, ?, ?)
      """;
  public static final String INSERT_BAGGAGE = """
    INSERT INTO baggages(type, quantity, segment_passengers_id) 
    VALUES(?, ?, ?)
      """;
  public static final String INSERT_PASSENGER = """
    INSERT INTO passengers(type, title, phone_number, id, given_name, gender, family_name, email, born_on, orders_id) 
    VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
      """;
  public static final String INSERT_CONDITION = """
    INSERT INTO conditions(orders_id) 
    VALUES(?)
      """;
  public static final String FIND_CONDITION_BY_ORDER_ID = """
    SELECT * FROM conditions WHERE orders_id = ?
      """;
  public static final String INSERT_REFUND_BEFORE_DEPARTURE = """
    INSERT INTO refund_before_departure(penalty_currency, penalty_amount, allowed, conditions_id) 
    VALUES(?, ?, ?, ?)
      """;
  public static final String INSERT_CHANGE_BEFORE_DEPARTURE = """
    INSERT INTO change_before_departure(penalty_currency, penalty_amount, allowed, conditions_id) 
    VALUES(?, ?, ?, ?)
      """;
  
  public static final String FIND_ORDER_BY_USER_ID = """
    SELECT * FROM orders WHERE users_id = ?
      """;
  public static final String FIND_SLICE_BY_ORDER_ID = """
    SELECT * FROM slices WHERE orders_id = ?
      """;
  public static final String FIND_SEGMENT_BY_SLICE_ID = """
    SELECT * FROM segments WHERE slices_id = ?
      """;
  public static final String FIND_SEGMENT_PASSENGERS_BY_SEGMENT_ID = """
    SELECT * FROM segment_passengers WHERE segments_id = ?
      """;
  public static final String FIND_BAGGAGE_BY_SEGMENT_PASSENGER_ID = """
    SELECT * FROM baggages WHERE segment_passengers_id = ?
      """;
  public static final String FIND_OPERATING_CARRIER_BY_SEGMENT_ID = """
    SELECT 
    operating_carrier.name, logo_symbol_url, logo_lockup_url, operating_carrier.id, iata_code, 
    conditions_of_carriage_url FROM operating_carrier 
    LEFT JOIN 
    segments ON operating_carrier.id = segments.operating_carrier_id 
    WHERE segments.id = ?
      """;
  public static final String FIND_MARKETING_CARRIER_BY_SEGMENT_ID = """
    SELECT 
    marketing_carrier.name, logo_symbol_url, logo_lockup_url, marketing_carrier.id, iata_code, 
    conditions_of_carriage_url FROM marketing_carrier 
    LEFT JOIN 
    segments ON marketing_carrier.id = segments.marketing_carrier_id 
    WHERE segments.id = ?
      """;
  public static final String FIND_AIRCRAFT_BY_SEGMENT_ID = """
    SELECT 
    aircraft.name, aircraft.id, iata_code FROM aircraft 
    LEFT JOIN 
    segments ON aircraft.id = segments.aircraft_id 
    WHERE segments.id = ?
      """;
  public static final String FIND_PASSENGER_BY_ORDER_ID = """
    SELECT * FROM passengers WHERE orders_id = ?
      """;
  public static final String FIND_REFUND_BEFORE_DEPARTURE_BY_CONDITION_ID = """
    SELECT * FROM refund_before_departure WHERE conditions_id = ?
      """;
  public static final String FIND_CHANGE_BEFORE_DEPARTURE_BY_CONDITION_ID = """
    SELECT * FROM change_before_departure WHERE conditions_id = ?
      """;
  public static final String FIND_OWNER_BY_ORDER_ID = """
    SELECT
    owners.name, logo_symbol_url, logo_lockup_url, owners.id, iata_code, conditions_of_carriage_url FROM owners
    LEFT JOIN
    orders ON owners.id = orders.owners_id
    WHERE orders.id = ?
      """;
}
