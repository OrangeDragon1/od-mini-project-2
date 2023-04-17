import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'duration'
})
export class DurationPipe implements PipeTransform {

  REGEX = /P(?:(\d+)Y)?(?:(\d+)M)?(?:(\d+)W)?(?:(\d+)D)?(?:T(?:(\d+)H)?(?:(\d+)M)?(?:(\d+)S)?)?$/;

  transform(value: string): string {
    return this.parseDuration(value);
  }

  parseDuration(input: string) {
    const [, years, months, weeks, days, hours, mins, secs] =
      input.match(this.REGEX) || [];
    return [
      ...(years ? [`${years} years`] : []),
      ...(months ? [`${months} months`] : []),
      ...(weeks ? [`${weeks} weeks`] : []),
      ...(days ? [`${days}d`] : []),
      // ...(hours || mins || secs
      //   ? [
      //       [hours || '00', mins || '00', secs || '00']
      //         .map((num) => (num.length < 2 ? `0${num}` : num))
      //         .join(':'),
      //     ]
      //   : []),
      ...(hours ? [`${hours.padStart(2, '0')}h`] : []),
      ...(mins ? [`${mins.padStart(2, '0')}m`] : []),
    ].join(' ');
  }
  
}
