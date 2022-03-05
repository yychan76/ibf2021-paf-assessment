#!/usr/bin/env python

import yfinance as yf
from pprint import pprint


def get_ticker(stock_name):
    """Returns the Ticker object for a given stock_name
    """
    return yf.Ticker(stock_name)


def get_info(stock_name):
    """Returns stock information for a given stock_name
    """
    return get_ticker(stock_name).info


def get_actions(stock_name):
    """Returns dividends and stock splits for a given stock_name
    """
    return get_ticker(stock_name).actions.to_dict()


def get_splits(stock_name):
    """Returns stock splits for a given stock_name
    """
    try:
        return get_ticker(stock_name).splits.to_dict()
    except AttributeError:
        # if there are no results the ticker returns an empty list, so return empty dict
        return {}


def get_dividends(stock_name):
    """Returns dividends for a given stock_name
    """
    try:
        return get_ticker(stock_name).dividends.to_dict()
    except AttributeError:
        # if there are no results the ticker returns an empty list, so return empty dict
        return {}

def get_history(
    stock_name,
    period="max",
    interval="1d",
    start=None,
    end=None,
    prepost=False,
    auto_adjust=True,
    actions=False,
):
    """Returns open, high, low, close, volume history for stock_name

    Keyword Arguments:
    period:  valid values are 1d, 5d, 1mo, 3mo, 6mo, 1y, 2y, 5y, 10y, ytd, max
    interval: value values are 1m, 2m, 5m, 15m, 30m, 60m, 90m, 1h, 1d, 5d, 1wk, 1mo, 3mo
              (intraday data cannot extend last 60 days)
    start: If not using period - Download start date string (YYYY-MM-DD) or datetime.
    end: If not using period - Download end date string (YYYY-MM-DD) or datetime.
    prepost: Include Pre and Post market data in results
    auto_adjust: Adjust all OHLC automatically
    actions: Download stock dividends and stock splits events

    Returns:
    dict
    """
    return (
        get_ticker(stock_name)
        .history(
            period=period,
            interval=interval,
            start=start,
            end=end,
            prepost=prepost,
            auto_adjust=auto_adjust,
            actions=actions,
        )
        .to_dict()
    )


if __name__ == "__main__":
    pprint(get_history("NVDA"))
