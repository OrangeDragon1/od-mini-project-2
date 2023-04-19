import { NgModule } from '@angular/core';

import { StyleClassModule } from 'primeng/styleclass';
import { MenubarModule } from 'primeng/menubar';
import { AccordionModule } from 'primeng/accordion';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { PanelModule } from 'primeng/panel';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { VirtualScrollerModule } from 'primeng/virtualscroller';
import { DividerModule } from 'primeng/divider';
import { CarouselModule } from 'primeng/carousel';
import { RadioButtonModule } from 'primeng/radiobutton';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ChipModule } from 'primeng/chip';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { InputMaskModule } from 'primeng/inputmask';
import { CalendarModule } from 'primeng/calendar';

const primengModules: any[] = [
  MenubarModule,
  AccordionModule,
  CardModule,
  ButtonModule,
  PanelModule,
  StyleClassModule,
  ScrollPanelModule,
  VirtualScrollerModule,
  DividerModule,
  CarouselModule,
  RadioButtonModule,
  BreadcrumbModule,
  ChipModule,
  DropdownModule,
  InputTextModule,
  InputMaskModule,
  CalendarModule
]

@NgModule({
  imports: primengModules,
  exports: primengModules
})
export class PrimeNGModule { }