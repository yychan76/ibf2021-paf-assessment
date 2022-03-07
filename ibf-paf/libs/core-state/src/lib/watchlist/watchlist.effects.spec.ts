import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Action } from '@ngrx/store';
import { provideMockStore } from '@ngrx/store/testing';
import { NxModule } from '@nrwl/angular';
import { hot } from 'jasmine-marbles';
import { Observable } from 'rxjs';

import * as WatchlistActions from './watchlist.actions';
import { WatchlistEffects } from './watchlist.effects';

describe('WatchlistEffects', () => {
  let actions: Observable<Action>;
  let effects: WatchlistEffects;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [NxModule.forRoot()],
      providers: [
        WatchlistEffects,
        provideMockActions(() => actions),
        provideMockStore(),
      ],
    });

    effects = TestBed.inject(WatchlistEffects);
  });

  describe('init$', () => {
    it('should work', () => {
      actions = hot('-a-|', { a: WatchlistActions.init() });

      const expected = hot('-a-|', {
        a: WatchlistActions.loadWatchlistSuccess({ watchlist: [] }),
      });

      expect(effects.init$).toBeObservable(expected);
    });
  });
});
