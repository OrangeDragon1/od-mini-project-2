package vttp2022.batch2a.miniproject2.server.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.batch2a.miniproject2.server.models.duffel.Aircraft;
import vttp2022.batch2a.miniproject2.server.models.duffel.Carrier;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders.Order;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders.OrderConditions;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders.OrderConditionsChangeBeforeDeparture;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders.OrderConditionsRefundBeforeDeparture;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders.OrderPassenger;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders.OrderSlice;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders.OrderSliceSegment;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders.OrderSliceSegmentPassenger;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders.OrderSliceSegmentPassengerBaggage;

@Repository
public class OrderRepository {

  @Autowired private JdbcTemplate jdbcTemplate;

  @Transactional
  public void addOrder(Order o, String userId) {
    Order order = o;
    Carrier owner = order.getOwner();
    List<OrderSlice> slices = o.getSlices();
    List<OrderPassenger> passengers = o.getPassengers();
    OrderConditions conditions = o.getConditions();
    SqlRowSet rs;

    rs = jdbcTemplate.queryForRowSet(Queries.FIND_OWNER_BY_ID, owner.getId());

    if (!rs.next()) {
      jdbcTemplate.update(Queries.INSERT_OWNER, owner.getName(), owner.getLogoSymbolUrl(), owner.getLogoLockupUrl(), owner.getId(), owner.getIataCode(), owner.getConditionsOfCarriageUrl());
    }

    rs = jdbcTemplate.queryForRowSet(Queries.FIND_ORDER_BY_ID, o.getId());
    if (!rs.next()) {
      jdbcTemplate.update(Queries.INSERT_ORDER, o.getTotalCurrency(), o.getTotalAmount(), o.getTaxCurrency(), o.getTaxAmount(), owner.getId(), o.getId(), o.getCreatedAt(), o.getBookingReference(), o.getBaseCurrency(), o.getBaseAmount(), userId);

      for (OrderSlice slice : slices) {
        jdbcTemplate.update(Queries.INSERT_SLICE, slice.getOriginName(), slice.getOriginIataCountryCode(), slice.getOriginIataCode(), slice.getId(), slice.getFareBrandName(), slice.getDuration(), slice.getDestinationName(), slice.getDestinationIataCountryCode(), slice.getDestinationIataCode(), o.getId());

        List<OrderSliceSegment> segments = slice.getSegments();
        for (OrderSliceSegment seg : segments) {

          Carrier operatingCarrier = seg.getOperatingCarrier();
          if (null != operatingCarrier) {
            rs = jdbcTemplate.queryForRowSet(Queries.FIND_OPERATING_CARRIER_BY_ID, operatingCarrier.getId());
            if (!rs.next()) {
              jdbcTemplate.update(Queries.INSERT_OPERATING_CARRIER, operatingCarrier.getName(), operatingCarrier.getLogoSymbolUrl(), operatingCarrier.getLogoLockupUrl(), operatingCarrier.getId(), operatingCarrier.getIataCode(), operatingCarrier.getConditionsOfCarriageUrl());
            }
          } else {
            operatingCarrier = new Carrier();
          }

          Carrier marketingCarrier = seg.getMarketingCarrier();
          rs = jdbcTemplate.queryForRowSet(Queries.FIND_MARKETING_CARRIER_BY_ID, marketingCarrier.getId());
          if (!rs.next()) {
            jdbcTemplate.update(Queries.INSERT_MARKETING_CARRIER, marketingCarrier.getName(), marketingCarrier.getLogoSymbolUrl(), marketingCarrier.getLogoLockupUrl(), marketingCarrier.getId(), marketingCarrier.getIataCode(), marketingCarrier.getConditionsOfCarriageUrl());
          }

          Aircraft aircraft = seg.getAircraft();
          if (null != aircraft) {
            rs = jdbcTemplate.queryForRowSet(Queries.FIND_AIRCRAFT_BY_ID, aircraft.getId());
            if (!rs.next()) {
              jdbcTemplate.update(Queries.INSERT_AIRCRAFT, aircraft.getName(), aircraft.getId(), aircraft.getIataCode());
            }
          } else {
            aircraft = new Aircraft();
          }

          jdbcTemplate.update(Queries.INSERT_SEGMENT, seg.getOriginTerminal(), seg.getOriginName(), seg.getOriginIataCountryCode(), seg.getOriginIataCode(), seg.getOperatingCarrierFlightNumber(), operatingCarrier.getId(), seg.getMarketingCarrierFlightNumber(), marketingCarrier.getId(), seg.getId(), seg.getDuration(), seg.getDistance(), seg.getDestinationTerminal(), seg.getDestinationName(), seg.getDestinationIataCountryCode(), seg.getDestinationIataCode(), seg.getDepartingAt(), seg.getArrivingAt(), aircraft.getId(), slice.getId());

          List<OrderSliceSegmentPassenger> segmentPassengers = seg.getPassengers();
          for (OrderSliceSegmentPassenger p : segmentPassengers) {
            jdbcTemplate.update(Queries.INSERT_SEGMENT_PASSENGER, p.getPassengerId(), p.getCabinClassMarketingName(), p.getCabinClass(), seg.getId());

            List<OrderSliceSegmentPassengerBaggage> baggages = p.getBaggages();
            for (OrderSliceSegmentPassengerBaggage b : baggages) {
              jdbcTemplate.update(Queries.INSERT_BAGGAGE, b.getType(), b.getQuantity(), p.getPassengerId());
            }
          }
        }
      }

      for (OrderPassenger p : passengers) {
        jdbcTemplate.update(Queries.INSERT_PASSENGER, p.getType(), p.getTitle(), p.getPhoneNumber(), p.getId(), p.getGivenName(), p.getGender(), p.getFamilyName(), p.getEmail(), p.getBornOn(), o.getId());
      }

      jdbcTemplate.update(Queries.INSERT_CONDITION, o.getId()); 
      rs = jdbcTemplate.queryForRowSet(Queries.FIND_CONDITION_BY_ORDER_ID, o.getId());
      int conditionsId = 0;
      while(rs.next()) {
        conditionsId = rs.getInt("id");
      }
      
      OrderConditionsRefundBeforeDeparture refund = conditions.getRefundBeforeDeparture();
      OrderConditionsChangeBeforeDeparture change = conditions.getChangeBeforeDeparture();

      if (null != refund) {
        jdbcTemplate.update(Queries.INSERT_REFUND_BEFORE_DEPARTURE, refund.getPenaltyCurrency(), refund.getPenaltyAmount(), refund.getAllowed(), conditionsId);
      }

      if (null != change) {
      jdbcTemplate.update(Queries.INSERT_CHANGE_BEFORE_DEPARTURE, change.getPenaltyCurrency(), change.getPenaltyAmount(), change.getAllowed(), conditionsId);
      }
    }    
  }

  @Transactional
  public List<Order> getOrdersByUserId(String userId) {

    List<Order> orders = new LinkedList<>();
    SqlRowSet ordersRS = jdbcTemplate.queryForRowSet(Queries.FIND_ORDER_BY_USER_ID, userId);

    while(ordersRS.next()) {
      Order order = Order.create(ordersRS);

      List<OrderSlice> slices = new LinkedList<>();
      SqlRowSet slicesRS = jdbcTemplate.queryForRowSet(Queries.FIND_SLICE_BY_ORDER_ID, order.getId());

      while(slicesRS.next()) {
        OrderSlice slice = OrderSlice.create(slicesRS);

        List<OrderSliceSegment> segments = new LinkedList<>();
        SqlRowSet segmentsRS = jdbcTemplate.queryForRowSet(Queries.FIND_SEGMENT_BY_SLICE_ID, slice.getId());
        
        while(segmentsRS.next()) {
          OrderSliceSegment seg = OrderSliceSegment.create(segmentsRS);

          List<OrderSliceSegmentPassenger> segPassengers = new LinkedList<>();
          SqlRowSet segPRS = jdbcTemplate.queryForRowSet(Queries.FIND_SEGMENT_PASSENGERS_BY_SEGMENT_ID, seg.getId());

          while(segPRS.next()) {
            OrderSliceSegmentPassenger segP = OrderSliceSegmentPassenger.create(segPRS);

            List<OrderSliceSegmentPassengerBaggage> baggages = new LinkedList<>();
            SqlRowSet baggagesRS = jdbcTemplate.queryForRowSet(Queries.FIND_BAGGAGE_BY_SEGMENT_PASSENGER_ID, segP.getPassengerId());

            while(baggagesRS.next()) {
              OrderSliceSegmentPassengerBaggage b = OrderSliceSegmentPassengerBaggage.create(baggagesRS);
              baggages.add(b);
            }
            segP.setBaggages(baggages);
            segPassengers.add(segP);
          }

          seg.setPassengers(segPassengers);
          SqlRowSet oCarrierRS = jdbcTemplate.queryForRowSet(Queries.FIND_OPERATING_CARRIER_BY_SEGMENT_ID, seg.getId());

          while(oCarrierRS.next()) {
            Carrier operatingCarrier = Carrier.create(oCarrierRS);
            seg.setOperatingCarrier(operatingCarrier);
          }

          SqlRowSet mCarrierRS = jdbcTemplate.queryForRowSet(Queries.FIND_MARKETING_CARRIER_BY_SEGMENT_ID, seg.getId());

          while(mCarrierRS.next()) {
            Carrier marketingCarrier = Carrier.create(mCarrierRS);
            seg.setMarketingCarrier(marketingCarrier);
          }

          SqlRowSet aircraftRS = jdbcTemplate.queryForRowSet(Queries.FIND_AIRCRAFT_BY_SEGMENT_ID, seg.getId());

          while(aircraftRS.next()) {
            Aircraft aircraft = Aircraft.create(aircraftRS);
            seg.setAircraft(aircraft);
          }

          segments.add(seg);

        }
        slice.setSegments(segments);

        String dateDepartingAt = slice.getSegments().get(0).getDepartingAt();
        String dateArrivingAt = slice.getSegments().get(slice.getSegments().size() - 1).getArrivingAt();
        
        slice.setPlusDays(OrderSlice.ifPlusDays(dateDepartingAt, dateArrivingAt));
        slice.setLayovers(OrderSlice.ifLayovers(slice.getSegments(), slice.getDestinationIataCode()));

        slices.add(slice);
      }

      List<OrderPassenger> passengers = new LinkedList<>();
      SqlRowSet passengersRS = jdbcTemplate.queryForRowSet(Queries.FIND_PASSENGER_BY_ORDER_ID, order.getId());
      while(passengersRS.next()) {
        OrderPassenger passenger = OrderPassenger.create(passengersRS);
        passengers.add(passenger);
      }
      order.setPassengers(passengers);

      OrderConditions conditions = new OrderConditions();
      int conditionsId = 0;
      SqlRowSet conditionsRS = jdbcTemplate.queryForRowSet(Queries.FIND_CONDITION_BY_ORDER_ID, order.getId());
      while(conditionsRS.next()) {
        conditionsId = conditionsRS.getInt("id");
      }

      SqlRowSet refundRS = jdbcTemplate.queryForRowSet(Queries.FIND_REFUND_BEFORE_DEPARTURE_BY_CONDITION_ID, conditionsId);
      while(refundRS.next()) {
        OrderConditionsRefundBeforeDeparture refund = OrderConditionsRefundBeforeDeparture.create(refundRS);
        conditions.setRefundBeforeDeparture(refund);
      }

      SqlRowSet changeRS = jdbcTemplate.queryForRowSet(Queries.FIND_CHANGE_BEFORE_DEPARTURE_BY_CONDITION_ID, conditionsId);
      while(changeRS.next()) {
        OrderConditionsChangeBeforeDeparture change = OrderConditionsChangeBeforeDeparture.create(changeRS);
        conditions.setChangeBeforeDeparture(change);
      }

      SqlRowSet ownerRS = jdbcTemplate.queryForRowSet(Queries.FIND_OWNER_BY_ORDER_ID, order.getId());
      while(ownerRS.next()) {
        Carrier owner = Carrier.create(ownerRS);
        order.setOwner(owner);
      }

      order.setConditions(conditions);
      order.setSlices(slices);
      orders.add(order);
    }

    return orders;
  }

  @Transactional
  public Order getOrdersByBookingRef(String bookingRef) {

    List<Order> orders = new LinkedList<>();
    SqlRowSet ordersRS = jdbcTemplate.queryForRowSet(Queries.FIND_ORDER_BY_BOOKING_REFERENCE, bookingRef);

    while(ordersRS.next()) {
      Order order = Order.create(ordersRS);

      List<OrderSlice> slices = new LinkedList<>();
      SqlRowSet slicesRS = jdbcTemplate.queryForRowSet(Queries.FIND_SLICE_BY_ORDER_ID, order.getId());

      while(slicesRS.next()) {
        OrderSlice slice = OrderSlice.create(slicesRS);

        List<OrderSliceSegment> segments = new LinkedList<>();
        SqlRowSet segmentsRS = jdbcTemplate.queryForRowSet(Queries.FIND_SEGMENT_BY_SLICE_ID, slice.getId());
        
        while(segmentsRS.next()) {
          OrderSliceSegment seg = OrderSliceSegment.create(segmentsRS);

          List<OrderSliceSegmentPassenger> segPassengers = new LinkedList<>();
          SqlRowSet segPRS = jdbcTemplate.queryForRowSet(Queries.FIND_SEGMENT_PASSENGERS_BY_SEGMENT_ID, seg.getId());

          while(segPRS.next()) {
            OrderSliceSegmentPassenger segP = OrderSliceSegmentPassenger.create(segPRS);

            List<OrderSliceSegmentPassengerBaggage> baggages = new LinkedList<>();
            SqlRowSet baggagesRS = jdbcTemplate.queryForRowSet(Queries.FIND_BAGGAGE_BY_SEGMENT_PASSENGER_ID, segP.getPassengerId());

            while(baggagesRS.next()) {
              OrderSliceSegmentPassengerBaggage b = OrderSliceSegmentPassengerBaggage.create(baggagesRS);
              baggages.add(b);
            }
            segP.setBaggages(baggages);
            segPassengers.add(segP);
          }

          seg.setPassengers(segPassengers);
          SqlRowSet oCarrierRS = jdbcTemplate.queryForRowSet(Queries.FIND_OPERATING_CARRIER_BY_SEGMENT_ID, seg.getId());

          while(oCarrierRS.next()) {
            Carrier operatingCarrier = Carrier.create(oCarrierRS);
            seg.setOperatingCarrier(operatingCarrier);
          }

          SqlRowSet mCarrierRS = jdbcTemplate.queryForRowSet(Queries.FIND_MARKETING_CARRIER_BY_SEGMENT_ID, seg.getId());

          while(mCarrierRS.next()) {
            Carrier marketingCarrier = Carrier.create(mCarrierRS);
            seg.setMarketingCarrier(marketingCarrier);
          }

          SqlRowSet aircraftRS = jdbcTemplate.queryForRowSet(Queries.FIND_AIRCRAFT_BY_SEGMENT_ID, seg.getId());

          while(aircraftRS.next()) {
            Aircraft aircraft = Aircraft.create(aircraftRS);
            seg.setAircraft(aircraft);
          }

          segments.add(seg);
        }
        slice.setSegments(segments);

        String dateDepartingAt = slice.getSegments().get(0).getDepartingAt();
        String dateArrivingAt = slice.getSegments().get(slice.getSegments().size() - 1).getArrivingAt();
        
        slice.setPlusDays(OrderSlice.ifPlusDays(dateDepartingAt, dateArrivingAt));
        slice.setLayovers(OrderSlice.ifLayovers(slice.getSegments(), slice.getDestinationIataCode()));

        slices.add(slice);
      }

      List<OrderPassenger> passengers = new LinkedList<>();
      SqlRowSet passengersRS = jdbcTemplate.queryForRowSet(Queries.FIND_PASSENGER_BY_ORDER_ID, order.getId());
      while(passengersRS.next()) {
        OrderPassenger passenger = OrderPassenger.create(passengersRS);
        passengers.add(passenger);
      }
      order.setPassengers(passengers);

      OrderConditions conditions = new OrderConditions();
      int conditionsId = 0;
      SqlRowSet conditionsRS = jdbcTemplate.queryForRowSet(Queries.FIND_CONDITION_BY_ORDER_ID, order.getId());
      while(conditionsRS.next()) {
        conditionsId = conditionsRS.getInt("id");
      }

      SqlRowSet refundRS = jdbcTemplate.queryForRowSet(Queries.FIND_REFUND_BEFORE_DEPARTURE_BY_CONDITION_ID, conditionsId);
      while(refundRS.next()) {
        OrderConditionsRefundBeforeDeparture refund = OrderConditionsRefundBeforeDeparture.create(refundRS);
        conditions.setRefundBeforeDeparture(refund);
      }

      SqlRowSet changeRS = jdbcTemplate.queryForRowSet(Queries.FIND_CHANGE_BEFORE_DEPARTURE_BY_CONDITION_ID, conditionsId);
      while(changeRS.next()) {
        OrderConditionsChangeBeforeDeparture change = OrderConditionsChangeBeforeDeparture.create(changeRS);
        conditions.setChangeBeforeDeparture(change);
      }

      SqlRowSet ownerRS = jdbcTemplate.queryForRowSet(Queries.FIND_OWNER_BY_ORDER_ID, order.getId());
      while(ownerRS.next()) {
        Carrier owner = Carrier.create(ownerRS);
        order.setOwner(owner);
      }

      order.setConditions(conditions);
      order.setSlices(slices);
      orders.add(order);
    }

    if (orders.size() < 1) {
      return null;
    }
    
    return orders.get(0);
  }

  @Transactional
  public boolean deleteOrderByBookingRef(String bookingRef) {
    int rowsAffected = jdbcTemplate.update(Queries.DELETE_ORDER_BY_BOOKING_REFERENCE, bookingRef);
    return rowsAffected > 0;
  }

}
