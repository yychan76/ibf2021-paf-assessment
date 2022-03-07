import { NgModule } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule, Store } from '@ngrx/store';
import { NxModule } from '@nrwl/angular';
import { readFirst } from '@nrwl/angular/testing';

import * as WatchlistActions from './watchlist.actions';
import { WatchlistEffects } from './watchlist.effects';
import { WatchlistFacade } from './watchlist.facade';
import { WatchlistEntity } from './watchlist.models';
import {
  WATCHLIST_FEATURE_KEY,
  State,
  initialState,
  reducer,
} from './watchlist.reducer';
import * as WatchlistSelectors from './watchlist.selectors';

interface TestSchema {
  watchlist: State;
}

describe('WatchlistFacade', () => {
  let facade: WatchlistFacade;
  let store: Store<TestSchema>;
  const createWatchlistEntity = (id: string, name = ''): WatchlistEntity => ({
    id,
    name: name || `name-${id}`,
  });

  describe('used in NgModule', () => {
    beforeEach(() => {
      @NgModule({
        imports: [
          StoreModule.forFeature(WATCHLIST_FEATURE_KEY, reducer),
          EffectsModule.forFeature([WatchlistEffects]),
        ],
        providers: [WatchlistFacade],
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
      facade = TestBed.inject(WatchlistFacade);
    });

    /**
     * The initially generated facade::loadAll() returns empty array
     */
    it('loadAll() should return empty list with loaded == true', async () => {
      let list = await readFirst(facade.allWatchlist$);
      let isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(0);
      expect(isLoaded).toBe(false);

      facade.init();

      list = await readFirst(facade.allWatchlist$);
      isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(0);
      expect(isLoaded).toBe(true);
    });

    /**
     * Use `loadWatchlistSuccess` to manually update list
     */
    it('allWatchlist$ should return the loaded list; and loaded flag == true', async () => {
      let list = await readFirst(facade.allWatchlist$);
      let isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(0);
      expect(isLoaded).toBe(false);

      store.dispatch(
        WatchlistActions.loadWatchlistSuccess({
          watchlist: [
            createWatchlistEntity('AAA'),
            createWatchlistEntity('BBB'),
          ],
        })
      );

      list = await readFirst(facade.allWatchlist$);
      isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(2);
      expect(isLoaded).toBe(true);
    });
  });
});
