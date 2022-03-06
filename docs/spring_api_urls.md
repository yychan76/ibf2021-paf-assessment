# Spring Boot API URLS

## 1. Stock Symbol Search

`GET /api/stocks/query?keywords=<search>`

## 2. Stock info

`GET /api/stocks/info/<stock_name>`

## 3. Stock Price History

`GET /api/stocks/prices/<stock_name>?period=<period>&interval=<interval>&start=<start>&end=<end>`

## 4. Stock Dividends History

`GET /api/stocks/dividends/<stock_name>`

## 5. Stock Splits History

`GET /api/stocks/splits/<stock_name>`

## 6. Create User

`POST /api/users`

## 7. Get All Users

`GET /api/users`

## 8. Get User By Id

`GET /api/users/<user_id>`

## 9. Update User

`PUT /api/users/<user_id>`

## 10. Delete User

`DELETE /api/users/<user_id>`

## 11. Create User Watchlist

`POST /api/users/<user_id>/watchlists`

## 12. Get User Watchlists

`GET /api/users/<user_id>/watchlists`

## 13. Get User Watchlist By Id

`GET /api/users/<user_id>/watchlists/<watchlist_id>`

## 14. Update User Watchlist

`PUT /api/users/<user_id>/watchlists/<watchlist_id>`

## 15. Delete User Watchlist

`DELETE /api/users/<user_id>/watchlists/<watchlist_id>`

## 16. Add User Watchlist Stocks

`POST /api/users/<user_id>/watchlists/<watchlist_id>/stocks`

## 17. Get User Watchlist Stocks

`GET /api/users/<user_id>/watchlists/<watchlist_id>/stocks`

## 18. Get User Watchlist Stock By Id

`GET /api/users/<user_id>/watchlists/<watchlist_id>/stocks/<w_stock_id>`

## 19. Delete User Watchlist Stock By Id

`DELETE /api/users/<user_id>/watchlists/<watchlist_id>/stocks/<w_stock_id>`

## 20. Create User Portfolio

`POST /api/users/<user_id>/portfolios`

## 21. Get User Portfolios

`GET /api/users/<user_id>/portfolios`

## 22. Get User Portfolio By Id

`GET /api/users/<user_id>/portfolios/<portfolio_id>`

## 23. Update User Portfolio

`PUT /api/users/<user_id>/portfolios/<portfolio_id>`

## 24. Delete User Portfolio

`DELETE /api/users/<user_id>/portfolios/<portfolio_id>`

## 25. Create User Portfolio Stock

`POST /api/users/<user_id>/portfolios/<portfolio_id>/stocks`

## 26. Get User Portfolio Stocks

`GET /api/users/<user_id>/portfolios/<portfolio_id>/stocks`

## 27. Get User Portfolio Stock By Id

`GET /api/users/<user_id>/portfolios/<portfolio_id>/stocks/<p_stock_id>`

## 28. Delete User Portfolio Stock

`DELETE /api/users/<user_id>/portfolios/<portfolio_id>/stocks/<p_stock_id>`

## 29. Create User Portfolio Stock Transaction

`POST /api/users/<user_id>/portfolios/<portfolio_id>/stocks/<p_stock_id>/transactions`

## 30. Get User Portfolio Stock Transactions

`GET /api/users/<user_id>/portfolios/<portfolio_id>/stocks/<p_stock_id>/transactions`

## 31. Get User Portfolio Stock Transaction By Id

`GET /api/users/<user_id>/portfolios/<portfolio_id>/stocks/<p_stock_id>/transactions/<transaction_id>`

## 32. Update User Portfolio Stock Transaction

`PUT /api/users/<user_id>/portfolios/<portfolio_id>/stocks/<p_stock_id>/transactions/<transaction_id>`

## 33. Delete User Portfolio Stock Transaction

`DELETE /api/users/<user_id>/portfolios/<portfolio_id>/stocks/<p_stock_id>/transactions/<transaction_id>`
