public interface IMarketClient {
    void buyStocks(String companyName, int count);

    void sellStocks(String companyName, int count);

    int getStocksPrice(String companyName);

    int getStocksCount(String companyName);
}
