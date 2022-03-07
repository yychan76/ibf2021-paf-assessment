import { NgModule } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule, Store } from '@ngrx/store';
import { NxModule } from '@nrwl/angular';
import { readFirst } from '@nrwl/angular/testing';

import * as PortfolioActions from './portfolio.actions';
import { PortfolioEffects } from './portfolio.effects';
import { PortfolioFacade } from './portfolio.facade';
import { PortfolioEntity } from './portfolio.models';
import {
  PORTFOLIO_FEATURE_KEY,
  State,
  initialState,
  reducer,
} from './portfolio.reducer';
import * as PortfolioSelectors from './portfolio.selectors';

interface TestSchema {
  portfolio: State;
}

describe('PortfolioFacade', () => {
  let facade: PortfolioFacade;
  let store: Store<TestSchema>;
  const createPortfolioEntity = (id: string, name = ''): PortfolioEntity => ({
    id,
    name: name || `name-${id}`,
  });

  describe('used in NgModule', () => {
    beforeEach(() => {
      @NgModule({
        imports: [
          StoreModule.forFeature(PORTFOLIO_FEATURE_KEY, reducer),
          EffectsModule.forFeature([PortfolioEffects]),
        ],
        providers: [PortfolioFacade],
      })
      class CustomFeatureModule {}

      @NgModule({
        imports: [
          NxModule.forRoot(),
          StoreModule.forRoot({}),
          EffectsModule.forRoot([]),
          CustomFeatureModule,
        ],
      })
      class RootModule {}
      TestBed.configureTestingModule({ imports: [RootModule] });

      store = TestBed.inject(Store);
      facade = TestBed.inject(PortfolioFacade);
    });

    /**
     * The initially generated facade::loadAll() returns empty array
     */
    it('loadAll() should return empty list with loaded == true', async () => {
      let list = await readFirst(facade.allPortfolio$);
      let isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(0);
      expect(isLoaded).toBe(false);

      facade.init();

      list = await readFirst(facade.allPortfolio$);
      isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(0);
      expect(isLoaded).toBe(true);
    });

    /**
     * Use `loadPortfolioSuccess` to manually update list
     */
    it('allPortfolio$ should return the loaded list; and loaded flag == true', async () => {
      let list = await readFirst(facade.allPortfolio$);
      let isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(0);
      expect(isLoaded).toBe(false);

      store.dispatch(
        PortfolioActions.loadPortfolioSuccess({
          portfolio: [
            createPortfolioEntity('AAA'),
            createPortfolioEntity('BBB'),
          ],
        })
      );

      list = await readFirst(facade.allPortfolio$);
      isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(2);
      expect(isLoaded).toBe(true);
    });
  });
});
