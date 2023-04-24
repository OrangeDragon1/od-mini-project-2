import { Aircraft, Carrier } from "./common.models";

export interface Order {
  totalCurrency: string;
  totalAmount: string;
  taxCurrency?: string;
  taxAmount?: string;
  slices: OrderSlice[];
  passengers: OrderPassenger[];
  owner: Carrier;
  id: string;
  createdAt: string;
  conditions: OrderConditions;
  bookingReference: string;
  baseCurrency?: string;
  baseAmount?: string;
}

export interface OrderConditions {
  refundBeforeDeparture?: OrderConditionsRefundBeforeDeparture;
  changeBeforeDeparture?: OrderConditionsChangeBeforeDeparture;
}

export interface OrderConditionsRefundBeforeDeparture {
  penaltyCurrency?: string;
  penaltyAmount?: string;
  allowed: boolean;
}

export interface OrderConditionsChangeBeforeDeparture {
  penaltyCurrency?: string;
  penaltyAmount?: string;
  allowed: boolean;
}

export interface OrderPassenger {
  type?: string;
  title: string;
  phoneNumber: string;
  id: string;
  givenName: string;
  gender: string;
  familyName: string;
  email: string;
  bornOn: string;
}

export interface OrderSlice {
  segments: OrderSliceSegment[];
  originName: string;
  originIataCountryCode: string;
  originIataCode: string;
  id: string;
  fareBrandName?: string;
  duration?: string;
  destinationName: string;
  destinationIataCountryCode: string;
  destinationIataCode: string;
  plusDays: number;
  layovers?: OrderSliceLayover[];
}

export interface OrderSliceLayover {
  duration: string
  layoverName: string
  layoverIataCode: string
}

export interface OrderSliceSegment {
  passengers: OrderSliceSegmentPassenger[];
  originTerminal?: string;
  originName: string;
  originIataCountryCode: string;
  originIataCode: string;
  operatingCarrierFlightNumber?: string;
  operatingCarrier?: Carrier;
  marketingCarrierFlightNumber: string;
  marketingCarrier: Carrier;
  id: string;
  duration: string;
  distance?: string;
  destinationTerminal?: string;
  destinationName: string;
  destinationIataCountryCode: string;
  destinationIataCode: string;
  departingAt: string;
  arrivingAt: string;
  aircraft?: Aircraft;
}

export interface OrderSliceSegmentPassenger {
  passengerId: string;
  cabinClassMarketingName?: string;
  cabinClass?: string;
  baggages: OrderSliceSegmentPassengerBaggage[];
}

export interface OrderSliceSegmentPassengerBaggage {
  type: string;
  quantity: number;
}
