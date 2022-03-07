import { PortfolioEntity } from './portfolio.models';
import {
  portfolioAdapter,
  PortfolioPartialState,
  initialState,
} from './portfolio.reducer';
import * as PortfolioSelectors from './portfolio.selectors';

describe('Portfolio Selectors', () => {
  const ERROR_MSG = 'No Error Available';
  const getPortfolioId = (it: PortfolioEntity) => it.id;
  const createPortfolioEntity = (id: string, name = '') =>
    ({
      id,
      name: name || `name-${id}`,
    } as PortfolioEntity);

  let state: PortfolioPartialState;

  beforeEach(() => {
    state = {
      portfolio: portfolioAdapter.setAll(
        [
          createPortfolioEntity('PRODUCT-AAA'),
          createPortfolioEntity('PRODUCT-BBB'),
          createPortfolioEntity('PRODUCT-CCC'),
        ],
        {
          ...initialState,
          selectedId: 'PRODUCT-BBB',
          error: ERROR_MSG,
          loaded: true,
        }
      ),
    };
  });

  describe('Portfolio Selectors', () => {
    it('getAllPortfolio() should return the list of Portfolio', () => {
      const results = PortfolioSelectors.getAllPortfolio(state);
      const selId = getPortfolioId(results[1]);

      expect(results.length).toBe(3);
      expect(selId).toBe('PRODUCT-BBB');
    });

    it('getSelected() should return the selected Entity', () => {
      const result = PortfolioSelectors.getSelected(state) as PortfolioEntity;
      const selId = getPortfolioId(result);

      expect(selId).toBe('PRODUCT-BBB');
    });

    it('getPortfolioLoaded() should return the current "loaded" status', () => {
      const result = PortfolioSelectors.getPortfolioLoaded(state);

      expect(result).toBe(true);
    });

    it('getPortfolioError() should return the current "error" state', () => {
      const result = PortfolioSelectors.getPortfolioError(state);

      expect(result).toBe(ERROR_MSG);
    });
  });
});
