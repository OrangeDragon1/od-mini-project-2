export enum PassengerType {
  adult = 'adult',
  child = 'child',
  infant_without_seat = 'infant_without_seat'
}

export enum CabinClass {
  first = 'first', 
  business = 'business', 
  premium_economy = 'premium_economy', 
  economy = 'economy'
}

export enum FareType {
  accompanying_adult, contract_bulk, contract_bulk_child, 
  contract_bulk_infant_with_seat, contract_bulk_infant_without_seat, 
  frequent_flyer, group_inclusive_tour, group_inclusive_tour_child, 
  humanitarian, individual_inclusive_tour_child, marine, seat_only, 
  student, teacher, tour_operator_inclusive, tour_operator_inclusive_infant, 
  unaccompanied_child, visiting_friends_and_family
}

export enum BaggageType {
  checked, carry_on
}

export interface Carrier {
  name: string
  logoSymbolUrl?: string
  logoLockupUrl?: string
  id: string
  iataCode?: string
  conditionsOfCarriageUrl?: string
}

export interface Aircraft {
  name: string
  id: string
  iataCode: string
}

export interface User { 
  id?: string
  firstName?: string
  lastName?: string
  email?: string
  role?: string
  verified?: boolean
  verificationString?: string
  resetPwdString?: string
}