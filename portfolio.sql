DROP DATABASE IF EXISTS portfoliodb;
CREATE DATABASE portfoliodb;
USE portfoliodb;
SELECT "Creating table user" AS "";
CREATE TABLE user (
    u_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(64) NOT NULL,
    pw_hash VARCHAR(60) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    display_name VARCHAR(64),
    avatar_url VARCHAR(256),
    UNIQUE KEY unique_email (email)
);
SELECT "Creating table portfolio" AS "";
CREATE TABLE portfolio (
    p_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    p_name VARCHAR(64) NOT NULL,
    u_id INTEGER,
    CONSTRAINT FK_UserPortfolio FOREIGN KEY(u_id) REFERENCES user(u_id) ON DELETE CASCADE
);
SELECT "Creating table portfolio_stock" AS "";
CREATE TABLE portfolio_stock (
    ps_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    symbol VARCHAR(64) NOT NULL,
    p_id INTEGER,
    CONSTRAINT FK_PortfolioStock FOREIGN KEY(p_id) REFERENCES portfolio(p_id) ON DELETE CASCADE,
    UNIQUE KEY unique_portfolio_stock (p_id, symbol)
);
SELECT "Creating table portfolio_stock_transaction" AS "";
CREATE TABLE portfolio_stock_transaction (
    pst_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    quantity FLOAT NOT NULL,
    price FLOAT,
    brokerage_fees FLOAT,
    tx_datetime DATETIME NOT NULL,
    ps_id INTEGER,
    CONSTRAINT FK_PortfolioStockTransaction FOREIGN KEY(ps_id) REFERENCES portfolio_stock(ps_id) ON DELETE CASCADE
);
SELECT "Creating table watchlist" AS "";
CREATE TABLE watchlist (
    w_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    w_name VARCHAR(64) NOT NULL,
    u_id INTEGER,
    CONSTRAINT FK_UserWatchlist FOREIGN KEY(u_id) REFERENCES user(u_id) ON DELETE CASCADE
);
SELECT "Creating table watchlist_stock" AS "";
CREATE TABLE watchlist_stock (
    ws_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    symbol VARCHAR(64) NOT NULL,
    w_id INTEGER,
    CONSTRAINT FK_WatchlistStock FOREIGN KEY(w_id) REFERENCES watchlist(w_id) ON DELETE CASCADE,
    UNIQUE KEY unique_watchlist_stock (w_id, symbol)
);
