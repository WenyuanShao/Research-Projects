import pandas as pd
import numpy as np
import matplotlib.pylab as plt
from matplotlib.pylab import rcParams
from statsmodels.tsa.stattools import adfuller
from statsmodels.tsa.seasonal import seasonal_decompose
from statsmodels.tsa.stattools import acf, pacf
from statsmodels.tsa.arima_model import ARIMA



dateparse = lambda dates: pd.datetime.strptime(dates, '%Y-%m')
data = pd.read_csv('data-pred.csv', parse_dates=['Date'], index_col='Date', date_parser=dateparse)


def test_stationarity(timeseries):

    rolmean = pd.rolling_mean(timeseries, window=12)
    rol_weighted_mean = pd.ewma(timeseries, span=12)
    rolstd = pd.rolling_std(timeseries, window=12)

    orig = plt.plot(timeseries, color='blue', label='Original')
    mean = plt.plot(rolmean, color='red', label='Rolling Mean')
    weighted_mean = plt.plot(rol_weighted_mean, color='green', label='weighted Mean')
    std = plt.plot(rolstd, color='black', label='Rolling Std')
    plt.legend(loc='best')
    plt.title('Rolling Mean & Standard Deviation')
    #plt.show(block=False)

    print 'Result of Dickry-Fuller test'
    dftest = adfuller(timeseries, autolag='AIC')
    dfoutput = pd.Series(dftest[0:4], index=['Test Statistic', 'p-value', '#Lags Used', 'Number of observations Used'])
    for key, value in dftest[4].items():
        dfoutput['Critical value(%s)' % key] = value
    print dfoutput


ts = data['T']
plt.plot(ts)
plt.show()
test_stationarity(ts)
plt.show()


# estimating
ts_log = np.log(ts)
# plt.plot(ts_log)
plt.show()
moving_avg = pd.rolling_mean(ts_log, 12)
# plt.plot(moving_avg)
# plt.plot(moving_avg,color='red')
plt.show()
ts_log_moving_avg_diff = ts_log - moving_avg
# print ts_log_moving_avg_diff.head(12)
ts_log_moving_avg_diff.dropna(inplace=True)
test_stationarity(ts_log_moving_avg_diff)
plt.show()

ts_log_diff = ts_log.diff(12)

ts_log_diff.dropna(inplace=True)
test_stationarity(ts_log_diff)
plt.show()

ts_log_diff1 = ts_log.diff(1)
#ts_log_diff2 = ts_log.diff(2)
ts_log_diff1.plot(color='blue')
#ts_log_diff2.plot(color='red')
#plt.title("dif 1 & 2")
plt.show()

decomposition = seasonal_decompose(ts_log)

trend = decomposition.trend
seasonal = decomposition.seasonal
residual = decomposition.resid

plt.plot(ts_log_diff)
plt.subplot(411)
plt.plot(ts_log,label='Original')
plt.legend(loc='best')
plt.subplot(412)
plt.plot(trend,label='Trend')
plt.legend(loc='best')
plt.subplot(413)
plt.plot(seasonal,label='Seasonarity')
plt.legend(loc='best')
plt.subplot(414)
plt.plot(residual,label='Residual')
plt.legend(loc='best')
plt.tight_layout()
plt.show()


lag_acf = acf(ts_log_diff, nlags=20)
lag_pacf = pacf(ts_log_diff, nlags=20, method='ols')
# q=1
plt.subplot(121)
plt.plot(lag_acf)
plt.axhline(y=0, linestyle='--', color='gray')
plt.axhline(y=-1.96 / np.sqrt(len(ts_log_diff)), linestyle='--', color='gray')  # lowwer
plt.axhline(y=1.96 / np.sqrt(len(ts_log_diff)), linestyle='--', color='gray')  # upper
plt.title('Autocorrelation Function')
# p=3
plt.subplot(122)
plt.plot(lag_pacf)
plt.axhline(y=0, linestyle='--', color='gray')
plt.axhline(y=-1.96 / np.sqrt(len(ts_log_diff)), linestyle='--', color='gray')
plt.axhline(y=1.96 / np.sqrt(len(ts_log_diff)), linestyle='--', color='gray')
plt.title('Partial Autocorrelation Function')
plt.tight_layout()
plt.show()

# AR model
model = ARIMA(ts_log, order=(0, 1, 2))
result_AR = model.fit(disp=-1)
plt.plot(ts_log_diff)
plt.plot(result_AR.fittedvalues, color='red')
plt.title('AR model')
plt.show()

# MA model
model = ARIMA(ts_log, order=(2, 1, 0))
result_MA = model.fit(disp=-1)
plt.plot(ts_log_diff)
plt.plot(result_MA.fittedvalues, color='red')
plt.title('MA model')
plt.show()

# ARIMA
model = ARIMA(ts_log, order=(0,1,2))#615
result_ARIMA = model.fit(disp=-1)
plt.plot(ts_log_diff)
plt.plot(result_ARIMA.fittedvalues, color='red')
plt.title('ARIMA')
plt.show()


predictions_ARIMA_diff = pd.Series(result_ARIMA.fittedvalues, copy=True)
#print predictions_ARIMA_diff.head()
predictions_ARIMA_diff_cumsum = predictions_ARIMA_diff.cumsum()
#print predictions_ARIMA_diff_cumsum.head()
predictions_ARIMA_log = pd.Series(ts_log.ix[0], index=ts_log.index)
predictions_ARIMA_log = predictions_ARIMA_log.add(predictions_ARIMA_diff_cumsum, fill_value=0)
#print predictions_ARIMA_log.tail()
predictions_ARIMA = np.exp(predictions_ARIMA_log)
print predictions_ARIMA.tail(12)
print '\n'
print ts.tail(12)
#print (predictions_ARIMA.tail(5) - ts.tail(5))  / (ts.tail(5))
plt.plot(ts)
plt.plot(predictions_ARIMA,color='black')
plt.title('predictions_ARIMA RMSE: %.4f' % np.sqrt(sum((predictions_ARIMA - ts) ** 2) / len(ts)))
plt.show()