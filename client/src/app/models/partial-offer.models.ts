import { Aircraft, BaggageType, CabinClass, Carrier, PassengerType } from "./common.models"

export interface PartialOffer{
  slices: PartialOfferSlice[]
  passengers: PartialOfferPassenger[]
  offers: PartialOfferOffer[]
  id: string
  cabinClass: CabinClass
}

export interface PartialOfferOffer {
  totalEmissionsKg: string
  totalCurrency: string
  totalAmount: string
  taxCurrency: string
  taxAmount: string
  slices: PartialOfferOfferSlice[]
  passengers: PartialOfferOfferPassenger[]
  passengerIdentityDocumentsRequired: boolean
  owner: Carrier
  id: string
  conditions: PartialOfferOfferConditions
  allowedPassengerIdentityDocumentTypes: string[]
}

export interface PartialOfferOfferPassenger {
  type: PassengerType
  id: string
}

export interface PartialOfferOfferConditions {
  changeBeforeDeparture?: PartialOfferOfferConditionsChangeOrRefund
  refundBeforeDeparture?: PartialOfferOfferConditionsChangeOrRefund
}

export interface PartialOfferOfferConditionsChangeOrRefund {
  allowed: boolean
  penaltyAmount?: string
  penaltyCurrency?: string
}

export interface PartialOfferOfferSlice {
  segments: PartialOfferOfferSliceSegment[]
  originIataCode: string
  id: string
  fareBrandName?: string
  duration?: string
  destinationIataCode: string
  plusDays: number
  layovers?: PartialOfferOfferSliceLayover[]
}

export interface PartialOfferOfferSliceLayover {
  duration: string
  layoverName: string
  layoverIataCode: string
}

export interface PartialOfferOfferSliceSegment {
  passengers: PartialOfferOfferSliceSegmentPassenger[]
  originName: string
  originIataCode: string
  operatingCarrierFlightNumber?: string
  operatingCarrier: Carrier
  marketingCarrierFlightNumber: string
  marketingCarrier: Carrier
  id: string
  duration: string
  destinationName: string
  destinationIataCode: string
  departingAt: string
  arrivingAt: string
  aircraft?: Aircraft
}

export interface PartialOfferOfferSliceSegmentPassenger {
  passengerId: string
  fareBasisCode?: string
  cabinClassMarketingName: string
  cabinClass: CabinClass
  baggages: PartialOfferOfferSliceSegmentPassengerBaggage[]
}

export interface PartialOfferOfferSliceSegmentPassengerBaggage {
  type: BaggageType
  quantity: number
}

export interface PartialOfferPassenger {
  type?: PassengerType
  id: string
  age?: number
}

export interface PartialOfferSlice {
  origin: string
  originName: string
  destination: string
  destinationName: string
  departureDate: string
}



