import { AbstractControl, FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";

export function enumValidator(enumType: any): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const value = control.value;
    if (value === '' || value === null || value === undefined) {
      return null;
    }
    const isValueValid = Object.values(enumType).includes(value);
    return isValueValid ? null : {'enumInvalid': {value}};
  };
}

export function nameLengthValidator(control: AbstractControl) {
  const givenName = control.get('givenName')?.value;
  const familyName = control.get('familyName')?.value;
  if (givenName && familyName && (givenName.length + familyName.length > 40)) {
    return { nameLengthError: true };
  }
  return null;
}