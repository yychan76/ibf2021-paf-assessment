export interface BaseEntity {
  id: string | null;
}

export interface Watchlist {
  wId?: string;
  wName?: string;
  stocks?: string[];
}

export interface User {
  uId: string;
  email: string;
  displayName: string;
  avatarUrl: string;
}

export interface StockSymbol {
  symbol: string;
  name: string;
  type: string;
  region: string;
  currency: string;
}

export interface ShareCounter {
  symbol?: string;
  name?: string;
  type?: string;
  region?: string;
  currency?: string;
  currentPrice?: number;
  previousClose?: number;
  priceChange?: number;
  prices?: any;
  dividends?: any;
  splits?: any;
  wsId?: string;
  wId?: string;
}

export interface Portfolio {
  pId?: string;
  pName?: string;
  stocks?: string[];
}
