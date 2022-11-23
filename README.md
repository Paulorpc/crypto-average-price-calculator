# crypto-average-price-calculator
This project aims to read one or more trade report's files (csv) from multiple exchanges at once, generating a
standartized output report and calculating the assets average price of USDx pairs.

### Supported Exchanges
[x] Binance
[x] Bitfinex
[ ] Bybit

## Understanding How it works
To run the app and process your data, first you need to export your trades reports from your exchange. Then will need
save every `.csv` files downloaded into your directory. By default, the app will read the reports from `c:` but, you 
can indicate **any path** where your reports are stored and the app will read and write the `outputReport.csv` into that
folder.

### Preparation Steps
1. Download de `.JAR` file from [path] and save in your computer;
2. Collect all exchanges trades reports and save then into your repository. Ex: c:\reports
3. open the terminal and run the app

### Running the app
```java
java appname.jar -jar -path {caminho}
```



