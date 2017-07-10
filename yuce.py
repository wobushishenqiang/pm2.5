#coding:utf-8
from __future__ import print_function
import pymysql
import threading
import urllib.request
import re
import time,json

import pandas as pd
import numpy as np
import statsmodels.api as sm
import sys
import datetime
from sklearn.preprocessing import StandardScaler
from sklearn.svm import SVR

sys.setdefaultencoding = 'utf-8'

def is_float(s):
    try:
        float(s)
    except:
        isFloat = False
    else:
        isFloat = True
    return isFloat

def fetchdata(city):
    print(city)
    while True: 
        url ='https://free-api.heweather.com/v5/weather?city='+city+'&key=97c2562a4e2746e381e2c2efd877b050'       
        req = urllib.request.Request(url)
        resp = urllib.request.urlopen(req).read()
        json_data = json.loads(resp)
	
        data = json_data['HeWeather5'][0]         
        pro = data['basic']['city']

        db = pymysql.connect(host="localhost", user="root", passwd="HHU123", db="pm25",  charset='utf8' )


        print(pro)
        try:
            sql2 = 'SELECT name,time,PM25VALUE,PM10VALUE,COVALUE,NO2VALUE,O3VALUE,SO2VALUE FROM pm25table  WHERE name = %s '
            cur = db.cursor()
            cur.execute(sql2, (pro.encode(encoding='utf-8')))
            print("e")
            result = cur.fetchall()
            print("chulichenggong")
            db.commit()
            result = tuple(result[::-1])

            #db.commit()
            #result = tuple(result[::-1])
            print(result)
            # ��������������c��¼��Ч�������� d��¼��������
            c = 0
            dta = pd.DataFrame(columns=('PM25', 'PM10', 'CO', 'NO2', 'O3', 'SO2'))
            for each in result:
                if is_float(each[2]) and is_float(each[3]) and is_float(each[4]) and is_float(each[5]) and is_float(each[6]) and is_float(each[7]):
                    dta.at[sm.tsa.datetools.date_parser(each[1], "%Y-%m-%d %H:%M:%S")] = float(each[2]), float(each[3]), float(each[4]), float(each[5]), float(each[6]), float(each[7])
                    c = c + 1
                else:
                    dta.at[sm.tsa.datetools.date_parser(each[1],"%Y-%m-%d %H:%M:%S")] = np.nan, np.nan, np.nan, np.nan, np.nan, np.nan

            print("success")
            d = len(dta)
            for l in range(1, 26):
             dta.at[dta.index[d - 2] + datetime.timedelta(hours=l)] = np.nan, np.nan, np.nan, np.nan, np.nan, np.nan
            dta = dta.astype(float)
            dta = dta.interpolate(method='linear', axis=0).ffill().bfill()
                    # dta.at[dta.index[j-2]+datetime.timedelta(hours=1)] = np.nan,np.nan,np.nan,np.nan,np.nan,np.nan
                    # print(dta.loc[dta.index[j-2]:,['PM10']])
                   
            feature_cols = ['PM10', 'CO', 'NO2', 'O3', 'SO2']
            print("smile")
            X = dta[feature_cols]
            y = dta['PM25']
                   
            X_scaler = StandardScaler()
            y_scaler = StandardScaler()
            X_train = X_scaler.fit_transform(X)
            y_train = y_scaler.fit_transform(y)
            X_test = X_scaler.transform(X[dta.index[d - 25]:dta.index[d - 1]])  # ͬ����ģ����ѵ��ת����������
            y_test = y_scaler.transform(y[dta.index[d - 25]:dta.index[d - 1]])
                    # ����svrģ��
            svr = SVR(kernel='rbf', C=1e3, gamma=0.1)
                    # svr.fit(X_train[:dta.index[j-2]-datetime.timedelta(hours=3)], y_train[:dta.index[j-2]-datetime.timedelta(hours=3)])
            svr.fit(X_train[:d +23], y_train[:d +23])
                    # y_svr = svr.predict(X_train[dta.index[j-2]-datetime.timedelta(hours=6):dta.index[j-2]])
                    # X_text�����ĵ�index_result��ΧҪһ��
            y_svr = svr.predict(X_test)
                    # ģ��Ԥ��inverse_transform���й�һ����ԭ
            y_result = pd.Series(y_scaler.inverse_transform(y_svr))

            index_result = pd.date_range(dta.index[d - 2] + datetime.timedelta(hours=1),
                                         dta.index[d - 2] + datetime.timedelta(hours=25), freq='H')
            y_result.index = pd.Index(index_result)


            print("hhhhh")
            sql3 = "INSERT INTO forecastpm25table(name,time,PM25VALUE,cityname) VALUES (%s,%s,%s,%s)"
            print("mmmmmm")
            print(y_result)
            Mytime = []
            Mypm = []
            pro1 = []
            city1=[]

            try:
             #for b in range(0,25):
                sql3 = "INSERT INTO forecastpm25table(name,time,PM25VALUE,cityname) VALUES (%s,%s,%s,%s)"
                print(str(y_result)[0 + 32 * 1:23 + 32 * 1])
                print("aaaa")

                try:
                    try:
                        try:
                            try:
                                try:
                                    try:
                                        try:
                                         cur.execute(sql3, (pro, str(y_result)[0 + 33 * 1:19 + 33 * 1], str(y_result)[23 + 33 * 1:32 + 33 * 1],city,))
                                        except:
                                         cur.execute(sql3, (pro, str(y_result)[0 + 33* 2:19 + 33 *2], str(y_result)[23 + 33 *2:32 + 33 * 2],city,))
                                    except:
                                      cur.execute(sql3, (pro, str(y_result)[0 + 33 * 3:19 + 33 * 3], str(y_result)[23 + 33 * 3:32 + 33 * 3], city,))
                                except:
                                 cur.execute(sql3,(pro, str(y_result)[0 + 33 * 4:19 + 33 * 4], str(y_result)[23 + 33 * 4:32 + 33 * 4], city,))
                            except:
                                cur.execute(sql3, (
                                pro, str(y_result)[0 + 33 * 5:19 + 33 * 5], str(y_result)[23 + 33 * 5:32 + 33 * 5],
                                city,))
                        except:
                            cur.execute(sql3, (pro, str(y_result)[0 + 33 * 6:19 + 33 * 6], str(y_result)[23 + 33 * 6:32 + 33 * 6], city,))
                    except:
                        cur.execute(sql3, (pro, str(y_result)[0 + 33 * 7:19 + 33 * 7], str(y_result)[23 + 33 * 7:32 + 33 * 7], city,))
                except:
                    cur.execute(sql3, (pro, str(y_result)[0 + 33 * 8:19 + 33 * 8], str(y_result)[23 + 33 * 8:32 + 33 * 8],city,))
                    print(str(y_result)[23 + 32 * 0:30 + 32 * 0])
                    db.commit()



            except Exception as e:
                print(e)
                cur.execute(sql3,
                            (pro, str(y_result)[0 + 33 * 9:19 + 33 * 9], str(y_result)[23 + 33 * 9:32 + 33 * 9], city,))
                db.rollback()


            print("预测成功")


        except Exception as e:
            cur.execute(sql3, (pro, str(y_result)[0 + 33 * 10:19 + 33 * 10], str(y_result)[23 + 33 * 10:32 + 33 * 10], city,))
            print(e)

            db.rollback()


        db.close()
        time.sleep(3600)



if __name__ == "__main__":
    file = open('C:/PMdata/citiess.txt','r')
    lines= file.readlines ()
    cities = []
    threads = []
    for line in lines:
        cities.append(line.strip())
    for city in cities:
        threads.append(threading.Thread(target = fetchdata,args =(city,)))
    for thread in threads:
        thread.start()
    for thread in threads:
        thread.join()