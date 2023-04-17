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
  BreadcrumbModule
]

@NgModule({
  imports: primengModules,
  exports: primengModules
})
export class PrimeNGModule { }