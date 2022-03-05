from flask import Flask, request, jsonify
from markupsafe import escape
from . import yahoodata as yd

app = Flask(__name__)


@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"


@app.route("/api/info/<stock_name>")
def get_info(stock_name):
    # flask will automatically jsonify dict return
    return yd.get_info(stock_name)


@app.route("/api/splits/<stock_name>")
def get_splits(stock_name):
    result = yd.get_splits(stock_name)
    items = [
        dict(date=k.to_pydatetime().isoformat(), timestamp=k.timestamp(), value=v)
        for k, v in result.items()
    ]
    return dict(splits=items, count=len(items))


@app.route("/api/dividends/<stock_name>")
def get_dividends(stock_name):
    result = yd.get_dividends(stock_name)
    items = [
        dict(date=k.to_pydatetime().isoformat(), timestamp=k.timestamp(), value=v)
        for k, v in result.items()
    ]
    return dict(dividends=items, count=len(items))


# deal with /api/history/<stock_name>?period=period&interval=interval&start=start&end=end
@app.route("/api/history/<stock_name>")
def get_history(stock_name):
    # get request params
    period = request.args.get("period")
    interval = request.args.get("interval")
    # start and end are in format YYYY-MM-DD
    start = request.args.get("start")
    end = request.args.get("end")
    kwargs = dict(period=period, interval=interval, start=start, end=end)
    # only feed in arguments that are not None
    result = yd.get_history(
        stock_name, **{k: v for k, v in kwargs.items() if v is not None}
    )
    # yfinance returns dict of price (open, high, low...) dicts
    # with pandas.TimeStamp as key and price as value
    # reorganise results to array of prices dicts
    items = [
        dict(
            date=timestamp.to_pydatetime().isoformat(),
            timestamp=timestamp.timestamp(),
            open=open_,
            high=high,
            low=low,
            close=close_,
            volume=volume,
        )
        for timestamp, open_, high, low, close_, volume in zip(
            result.get("Open").keys(),
            result.get("Open").values(),
            result.get("High").values(),
            result.get("Low").values(),
            result.get("Close").values(),
            result.get("Volume").values(),
        )
    ]
    return dict(prices=items, count=len(items))


if __name__ == "__main__":
    app.run()
