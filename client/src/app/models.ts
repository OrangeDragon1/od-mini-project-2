export interface Airport {
  name: string
  id: string
  timeZoneOffset: string
  iataCode: string
  geoCode: GeoCode
  address: Address
  travelersScore: number
}

export interface GeoCode {
  latitude: number
  longitude: number
}

export interface Address {
  cityName: string
  cityCode: string
  countryName: string
  countryCode: string
  stateCode: string
  regionCode: string
}