import { AbstractControl, ValidatorFn } from "@angular/forms";

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