# Flask API URLS

## Description

These are information reshaped from those provided by the  [yfinance](https://pypi.org/project/yfinance/) Python package as JSON data.

### Motivation

The free tier of 3rd party API providers of Yahoo Finance data via RapidAPI.com is too low. Yahoo stopped providing their finance API and there are scammer sites out there done in React collecting payment via Stripe that claims to provide Yahoo Finance data <https://www.scamadviser.com/check-website/yahoofinanceapi.com>

## 1. Stock info

`GET http://159.223.59.64/api/info/<stock_name>`

example

- <http://159.223.59.64/api/info/nvda>
- <http://159.223.59.64/api/info/msft>

```json
{
    "52WeekChange": 0.8334602,
    "SandP52WeekChange": 0.14270782,
    "address1": "2788 San Tomas Expressway",
    "algorithm": null,
    "annualHoldingsTurnover": null,
    "annualReportExpenseRatio": null,
    "ask": 240.4,
    "askSize": 1100,
    "averageDailyVolume10Day": 63569080,
    "averageVolume": 53057310,
    "averageVolume10days": 63569080,
    "beta": 1.383888,
    "beta3Year": null,
    "bid": 239.8,
    "bidSize": 800,
    "bookValue": 10.628,
    "category": null,
    "circulatingSupply": null,
    "city": "Santa Clara",
    "companyOfficers": [],
    "country": "United States",
    "currency": "USD",
    "currentPrice": 242.2,
    "currentRatio": 6.65,
    "dateShortInterest": 1644883200,
    "dayHigh": 244.09,
    "dayLow": 234.1663,
    "debtToEquity": 43.916,
    "dividendRate": 0.16,
    "dividendYield": 0.0007,
    "earningsGrowth": 1.04,
    "earningsQuarterlyGrowth": 1.061,
    "ebitda": 11214999552,
    "ebitdaMargins": 0.41669998,
    "enterpriseToEbitda": 51.569,
    "enterpriseToRevenue": 21.489,
    "enterpriseValue": 578343075840,
    "exDividendDate": 1646179200,
    "exchange": "NMS",
    "exchangeTimezoneName": "America/New_York",
    "exchangeTimezoneShortName": "EST",
    "expireDate": null,
    "fiftyDayAverage": 260.1464,
    "fiftyTwoWeekHigh": 346.47,
    "fiftyTwoWeekLow": 115.665,
    "financialCurrency": "USD",
    "fiveYearAverageReturn": null,
    "fiveYearAvgDividendYield": 0.24,
    "floatShares": 2404190560,
    "forwardEps": 6.72,
    "forwardPE": 36.041668,
    "freeCashflow": 6733125120,
    "fromCurrency": null,
    "fundFamily": null,
    "fundInceptionDate": null,
    "gmtOffSetMilliseconds": "-18000000",
    "grossMargins": 0.64929,
    "grossProfits": 17475000000,
    "heldPercentInsiders": 0.04078,
    "heldPercentInstitutions": 0.66699,
    "impliedSharesOutstanding": null,
    "industry": "Semiconductors",
    "isEsgPopulated": false,
    "lastCapGain": null,
    "lastDividendDate": 1638316800,
    "lastDividendValue": 0.04,
    "lastFiscalYearEnd": 1643500800,
    "lastMarket": null,
    "lastSplitDate": 1626739200,
    "lastSplitFactor": "4:1",
    "legalType": null,
    "logo_url": "https://logo.clearbit.com/nvidia.com",
    "longBusinessSummary": "NVIDIA Corporation operates as a visual computing company worldwide. It operates in two segments, Graphics and Compute & Networking. The Graphics segment offers GeForce GPUs for gaming and PCs, the GeForce NOW game streaming service and related infrastructure, and solutions for gaming platforms; Quadro/NVIDIA RTX GPUs for enterprise design; GRID software for cloud-based visual and virtual computing; and automotive platforms for infotainment systems. The Compute & Networking segment offers Data Center platforms and systems for AI, HPC, and accelerated computing; Mellanox networking and interconnect solutions; automotive AI Cockpit, autonomous driving development agreements, and autonomous vehicle solutions; and Jetson for robotics and other embedded platforms. The company's products are used in gaming, professional visualization, datacenter, and automotive markets. NVIDIA Corporation sells its products to original equipment manufacturers, original device manufacturers, system builders, add-in board manufacturers, retailers/distributors, Internet and cloud service providers, automotive manufacturers and tier-1 automotive suppliers, mapping companies, start-ups, and other ecosystem participants. NVIDIA has partnership with Google Cloud to create AI-on-5G Lab. NVIDIA Corporation was founded in 1993 and is headquartered in Santa Clara, California.",
    "longName": "NVIDIA Corporation",
    "market": "us_market",
    "marketCap": 603562377216,
    "maxAge": 1,
    "maxSupply": null,
    "messageBoardId": "finmb_32307",
    "morningStarOverallRating": null,
    "morningStarRiskRating": null,
    "mostRecentQuarter": 1643500800,
    "navPrice": null,
    "netIncomeToCommon": 9752000512,
    "nextFiscalYearEnd": 1706572800,
    "numberOfAnalystOpinions": 42,
    "open": 237.56,
    "openInterest": null,
    "operatingCashflow": 9107999744,
    "operatingMargins": 0.37308,
    "payoutRatio": 0.0416,
    "pegRatio": 1.32,
    "phone": "408 486 2000",
    "preMarketPrice": 237.58,
    "previousClose": 234.73,
    "priceHint": 2,
    "priceToBook": 22.788858,
    "priceToSalesTrailing12Months": 22.42559,
    "profitMargins": 0.36234,
    "quickRatio": 5.965,
    "quoteType": "EQUITY",
    "recommendationKey": "buy",
    "recommendationMean": 1.9,
    "regularMarketDayHigh": 244.09,
    "regularMarketDayLow": 234.1663,
    "regularMarketOpen": 237.56,
    "regularMarketPreviousClose": 234.73,
    "regularMarketPrice": 242.2,
    "regularMarketVolume": 38964185,
    "returnOnAssets": 0.17198999,
    "returnOnEquity": 0.44832,
    "revenueGrowth": 0.528,
    "revenuePerShare": 10.783,
    "revenueQuarterlyGrowth": null,
    "sector": "Technology",
    "sharesOutstanding": 2492000000,
    "sharesPercentSharesOut": 0.0106,
    "sharesShort": 26446744,
    "sharesShortPreviousMonthDate": 1642118400,
    "sharesShortPriorMonth": 25524396,
    "shortName": "NVIDIA Corporation",
    "shortPercentOfFloat": 0.011,
    "shortRatio": 0.48,
    "startDate": null,
    "state": "CA",
    "strikePrice": null,
    "symbol": "NVDA",
    "targetHighPrice": 400,
    "targetLowPrice": 177.5,
    "targetMeanPrice": 343.46,
    "targetMedianPrice": 350,
    "threeYearAverageReturn": null,
    "toCurrency": null,
    "totalAssets": null,
    "totalCash": 21208000512,
    "totalCashPerShare": 8.47,
    "totalDebt": 11687000064,
    "totalRevenue": 26914000896,
    "tradeable": false,
    "trailingAnnualDividendRate": 0.16,
    "trailingAnnualDividendYield": 0.00068163424,
    "trailingEps": 3.85,
    "trailingPE": 62.909092,
    "trailingPegRatio": 3.2209,
    "twoHundredDayAverage": 232.40248,
    "volume": 38964185,
    "volume24Hr": null,
    "volumeAllCurrencies": null,
    "website": "https://www.nvidia.com",
    "yield": null,
    "ytdReturn": null,
    "zip": "95051"
}
```

## 2. Stock Price History

`GET http://159.223.59.64/api/history/nvda?period=<period>&interval=<interval>&start=<start_date>&end=<end_date>`

```text
Request Params
--------------
period:  valid values are 1d, 5d, 1mo, 3mo, 6mo, 1y, 2y, 5y, 10y, ytd, max
interval: value values are 1m, 2m, 5m, 15m, 30m, 60m, 90m, 1h, 1d, 5d, 1wk, 1mo, 3mo
              (intraday data cannot extend last 60 days)
start: If not using period - Download start date string (YYYY-MM-DD) or datetime.
end: If not using period - Download end date string (YYYY-MM-DD) or datetime.
```

When using `period`, don't use `start` and `end` dates. Also `yfinance` seems to have a bug where the last record returned when using start and end dates does not fall within the period between start and end.

example

- <http://159.223.59.64/api/history/nvda?period=20d&interval=1h>
- <http://159.223.59.64/api/history/nvda?period=1y>
- <http://159.223.59.64/api/history/msft?period=max>
- <http://159.223.59.64/api/history/nvda?interval=1h&start=2022-02-01&end=2022-02-02>

```json
{
    "count": 8,
    "prices": [
        {
            "close": 242.3699951171875,
            "date": "2022-02-01 09:30:00-05:00",
            "high": 251.4499969482422,
            "low": 238.9001007080078,
            "open": 251.0399932861328,
            "timestamp": 1643725800.0,
            "volume": 19085290
        },
        {
            "close": 243.77499389648438,
            "date": "2022-02-01 10:30:00-05:00",
            "high": 245.61000061035156,
            "low": 239.79010009765625,
            "open": 242.4398956298828,
            "timestamp": 1643729400.0,
            "volume": 7932231
        },
        {
            "close": 243.143798828125,
            "date": "2022-02-01 11:30:00-05:00",
            "high": 245.97999572753906,
            "low": 242.4199981689453,
            "open": 243.74000549316406,
            "timestamp": 1643733000.0,
            "volume": 5466283
        },
        {
            "close": 243.89950561523438,
            "date": "2022-02-01 12:30:00-05:00",
            "high": 244.28990173339844,
            "low": 241.8000030517578,
            "open": 243.2799072265625,
            "timestamp": 1643736600.0,
            "volume": 3290770
        },
        {
            "close": 241.06640625,
            "date": "2022-02-01 13:30:00-05:00",
            "high": 243.95899963378906,
            "low": 239.85000610351562,
            "open": 243.89990234375,
            "timestamp": 1643740200.0,
            "volume": 3323904
        },
        {
            "close": 244.30999755859375,
            "date": "2022-02-01 14:30:00-05:00",
            "high": 244.42999267578125,
            "low": 240.3699951171875,
            "open": 241.0200958251953,
            "timestamp": 1643743800.0,
            "volume": 4430920
        },
        {
            "close": 246.39999389648438,
            "date": "2022-02-01 15:30:00-05:00",
            "high": 246.89999389648438,
            "low": 244.11000061035156,
            "open": 244.33999633789062,
            "timestamp": 1643747400.0,
            "volume": 4546468
        },
        {
            "close": 242.1999969482422,
            "date": "2022-03-02 16:00:00-05:00",
            "high": 242.1999969482422,
            "low": 242.1999969482422,
            "open": 242.1999969482422,
            "timestamp": 1646254800.0,
            "volume": 0
        }
    ]
}
```

## 3. Dividends

`GET http://159.223.59.64/api/dividends/<stock_name>`

example

- <http://159.223.59.64/api/dividends/nvda>
- <http://159.223.59.64/api/dividends/msft>

```json
{
    "count": 37,
    "dividends": [
        {
            "date": "2012-11-20 00:00:00",
            "timestamp": 1353369600.0,
            "value": 0.01875
        },
        {
            "date": "2013-02-26 00:00:00",
            "timestamp": 1361836800.0,
            "value": 0.01875
        },
        {
            "date": "2013-05-21 00:00:00",
            "timestamp": 1369094400.0,
            "value": 0.01875
        },
        {
            "date": "2013-08-20 00:00:00",
            "timestamp": 1376956800.0,
            "value": 0.01875
        },
        {
            "date": "2013-11-19 00:00:00",
            "timestamp": 1384819200.0,
            "value": 0.02125
        },
        {
            "date": "2014-02-25 00:00:00",
            "timestamp": 1393286400.0,
            "value": 0.02125
        },
        {
            "date": "2014-05-20 00:00:00",
            "timestamp": 1400544000.0,
            "value": 0.02125
        },
        {
            "date": "2014-08-19 00:00:00",
            "timestamp": 1408406400.0,
            "value": 0.02125
        },
        {
            "date": "2014-11-19 00:00:00",
            "timestamp": 1416355200.0,
            "value": 0.02125
        },
        {
            "date": "2015-02-24 00:00:00",
            "timestamp": 1424736000.0,
            "value": 0.02125
        },
        {
            "date": "2015-05-19 00:00:00",
            "timestamp": 1431993600.0,
            "value": 0.0245
        },
        {
            "date": "2015-08-18 00:00:00",
            "timestamp": 1439856000.0,
            "value": 0.0245
        },
        {
            "date": "2015-11-18 00:00:00",
            "timestamp": 1447804800.0,
            "value": 0.02875
        },
        {
            "date": "2016-02-29 00:00:00",
            "timestamp": 1456704000.0,
            "value": 0.02875
        },
        {
            "date": "2016-05-24 00:00:00",
            "timestamp": 1464048000.0,
            "value": 0.02875
        },
        {
            "date": "2016-08-23 00:00:00",
            "timestamp": 1471910400.0,
            "value": 0.02875
        },
        {
            "date": "2016-11-23 00:00:00",
            "timestamp": 1479859200.0,
            "value": 0.035
        },
        {
            "date": "2017-02-22 00:00:00",
            "timestamp": 1487721600.0,
            "value": 0.035
        },
        {
            "date": "2017-05-19 00:00:00",
            "timestamp": 1495152000.0,
            "value": 0.035
        },
        {
            "date": "2017-08-22 00:00:00",
            "timestamp": 1503360000.0,
            "value": 0.035
        },
        {
            "date": "2017-11-22 00:00:00",
            "timestamp": 1511308800.0,
            "value": 0.0375
        },
        {
            "date": "2018-02-22 00:00:00",
            "timestamp": 1519257600.0,
            "value": 0.0375
        },
        {
            "date": "2018-05-23 00:00:00",
            "timestamp": 1527033600.0,
            "value": 0.0375
        },
        {
            "date": "2018-08-29 00:00:00",
            "timestamp": 1535500800.0,
            "value": 0.0375
        },
        {
            "date": "2018-11-29 00:00:00",
            "timestamp": 1543449600.0,
            "value": 0.04
        },
        {
            "date": "2019-02-28 00:00:00",
            "timestamp": 1551312000.0,
            "value": 0.04
        },
        {
            "date": "2019-05-30 00:00:00",
            "timestamp": 1559174400.0,
            "value": 0.04
        },
        {
            "date": "2019-08-28 00:00:00",
            "timestamp": 1566950400.0,
            "value": 0.04
        },
        {
            "date": "2019-11-27 00:00:00",
            "timestamp": 1574812800.0,
            "value": 0.04
        },
        {
            "date": "2020-02-27 00:00:00",
            "timestamp": 1582761600.0,
            "value": 0.04
        },
        {
            "date": "2020-06-04 00:00:00",
            "timestamp": 1591228800.0,
            "value": 0.04
        },
        {
            "date": "2020-09-01 00:00:00",
            "timestamp": 1598918400.0,
            "value": 0.04
        },
        {
            "date": "2020-12-03 00:00:00",
            "timestamp": 1606953600.0,
            "value": 0.04
        },
        {
            "date": "2021-03-09 00:00:00",
            "timestamp": 1615248000.0,
            "value": 0.04
        },
        {
            "date": "2021-06-09 00:00:00",
            "timestamp": 1623196800.0,
            "value": 0.04
        },
        {
            "date": "2021-08-31 00:00:00",
            "timestamp": 1630368000.0,
            "value": 0.04
        },
        {
            "date": "2021-12-01 00:00:00",
            "timestamp": 1638316800.0,
            "value": 0.04
        }
    ]
}
```

## 4. Stock Splits

`GET http://159.223.59.64/api/splits/<stock_name>`

example

- <http://159.223.59.64/api/splits/nvda>
- <http://159.223.59.64/api/splits/msft>

```json
{
    "count": 5,
    "splits": [
        {
            "date": "2000-06-27 00:00:00",
            "timestamp": 962064000.0,
            "value": 2.0
        },
        {
            "date": "2001-09-12 00:00:00",
            "timestamp": 1000252800.0,
            "value": 2.0
        },
        {
            "date": "2006-04-07 00:00:00",
            "timestamp": 1144368000.0,
            "value": 2.0
        },
        {
            "date": "2007-09-11 00:00:00",
            "timestamp": 1189468800.0,
            "value": 1.5
        },
        {
            "date": "2021-07-20 00:00:00",
            "timestamp": 1626739200.0,
            "value": 4.0
        }
    ]
}
```

### Notes

- `timestamp` is POSIX timestamp as float
