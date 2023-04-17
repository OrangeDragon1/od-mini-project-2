import { CabinClass, FareType, PassengerType } from "./common.models"

export interface OfferRequest{
  slices: Slice[]
  passengers: Passenger[]
  maxConnections: number
  cabinClass: CabinClass
}

export interface Slice{
  origin: string
  destination: string
  departureTimeRange?: TimeRange
  departureDate: string // ISO 8601, YYYY-MM-DD
  arrivalTimeRange?: TimeRange
}

export interface TimeRange {
  to: string
  from: string
}

export interface Passenger {
  age?: number
  familyName?: string
  givenName?: string
  loyaltyProgrammeAccounts?: LoyaltyProgrammeAccount
  type?: PassengerType
  fareType?: FareType
}

export interface LoyaltyProgrammeAccount {
  accountNumber: string
  airlineIataCode: string
}