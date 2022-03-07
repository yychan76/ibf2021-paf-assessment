import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';

export const coreDataRoutes: Route[] = [];

@NgModule({
  imports: [CommonModule, RouterModule, HttpClientModule],
})
export class CoreDataModule {}
