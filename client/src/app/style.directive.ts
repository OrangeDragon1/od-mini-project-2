import { Directive, ElementRef, HostBinding } from "@angular/core";

@Directive({
  selector: '[click]'
})
export class StyleDirective {

  constructor(private el: ElementRef) { }

  @HostBinding('style.cursor') cursor: string = 'pointer';

}