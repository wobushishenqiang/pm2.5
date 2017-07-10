# coding:utf-8
from __future__ import print_function
import pymysql
import threading
import urllib.request
import re
import time

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
        temp='http://www.pm25.in/'+ city + '.html'
        #爬虫的站为：www.pm25.in,
        url = urllib.request.urlopen(temp)
        text = url.read().decode('utf-8')
        shuju = re.findall('<td>(.*?)</td>',text,re.S)#正则pm2.5等污染物数据
        #正则寻找当前时间 例如，2016-04-13 20:10:00
        data_time = re.findall("\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}",text,re.S)
        i = 1
        j = 0
        datas = []
        #在该路径下创建以城市命名的文件，以存储pm2.5数据
        tempdata = open('C:/PMdata/'+ city + '.txt','a')
        # 打开数据库连接
        db = pymysql.connect("localhost", "root", "HHU123", "pm25",  charset='utf8' )
        # 使用cursor()方法获取操作游标
        cursor = db.cursor()

        for each in shuju:
            datas.append(each)
            i += 1




            if i > 10:
                datas.append(data_time[0])

                i = 1
                j += 1

                tempdata.write(','.join(datas) + '\n')
                sql = "INSERT INTO pm25table(name,time,AQI,AQIKind,pollutant,PM25VALUE,PM10VALUE,COVALUE,NO2VALUE,O3VALUE,SO2VALUE,cityname) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                sql2 = 'SELECT name,time,PM25VALUE,PM10VALUE,COVALUE,NO2VALUE,O3VALUE,SO2VALUE FROM pm25table  WHERE name = %s '
                try:
                    # 执行sql语句
                    cursor.execute(sql, (
                    datas[0].encode(encoding='utf-8'), data_time[0], datas[1], datas[2], datas[3], datas[4], datas[5],
                    datas[6], datas[7], datas[8], datas[9], city))
                    db.commit()

                    # 使用cursor()方法获取操作游标
                    cursor = db.cursor()
                    cursor.execute(sql2, (datas[0].encode(encoding='utf-8')))
                    result = cursor.fetchall()
                    db.commit()

                    # 数据与索引处理c记录有效数据数量 d记录数据数量
                    c = 0

                    dta = pd.DataFrame(columns=('PM25', 'PM10', 'CO', 'NO2', 'O3', 'SO2'))
                    for each in result:
                        if is_float(each[2]) and is_float(each[3]) and is_float(each[4]) and is_float(
                                each[5]) and is_float(each[6]) and is_float(each[7]):

                            dta.at[sm.tsa.datetools.date_parser(each[1], "%Y-%m-%d %H:%M:%S")] = float(each[2]), float(
                                each[3]), float(each[4]), float(each[5]), float(each[6]), float(each[7])
                            c = c + 1
                        else:
                            dta.at[sm.tsa.datetools.date_parser(each[1],
                                                                "%Y-%m-%d %H:%M:%S")] = np.nan, np.nan, np.nan, np.nan, np.nan, np.nan

                    # 按索引缺失值插值填补 DataFrame的interpolate处理有问题

                    d = len(dta)
                    for l in range(1, 26):
                     dta.at[
                        dta.index[d - 2] + datetime.timedelta(hours=l)] = np.nan, np.nan, np.nan, np.nan, np.nan, np.nan
                    dta = dta.astype(float)

                    dta = dta.interpolate(method='linear', axis=0).ffill().bfill()

                    # dta.at[dta.index[j-2]+datetime.timedelta(hours=1)] = np.nan,np.nan,np.nan,np.nan,np.nan,np.nan
                    # print(dta.loc[dta.index[j-2]:,['PM10']])
                    # 获取特征字段并设置自变量 因变量
                    feature_cols = ['PM10', 'CO', 'NO2', 'O3', 'SO2']
                    X = dta[feature_cols]
                    y = dta['PM25']
                    # 模型归一化处理 多维向量需要归一化 归一化后无日期索引
                    X_scaler = StandardScaler()
                    y_scaler = StandardScaler()
                    X_train = X_scaler.fit_transform(X)
                    y_train = y_scaler.fit_transform(y)
                    X_test = X_scaler.transform(X[dta.index[d - 25]:dta.index[d - 1]])  # 同样的模型来训练转化测试数据
                    y_test = y_scaler.transform(y[dta.index[d - 25]:dta.index[d - 1]])

                    # 构建svr模型
                    svr = SVR(kernel='rbf', C=1e3, gamma=0.2)
                    # svr.fit(X_train[:dta.index[j-2]-datetime.timedelta(hours=3)], y_train[:dta.index[j-2]-datetime.timedelta(hours=3)])
                    svr.fit(X_train[:d +23], y_train[:d +23])
                    # y_svr = svr.predict(X_train[dta.index[j-2]-datetime.timedelta(hours=6):dta.index[j-2]])
                    # X_text与下文的index_result范围要一样
                    y_svr = svr.predict(X_test)
                    # 模型预测inverse_transform进行归一化还原
                    y_result = pd.Series(y_scaler.inverse_transform(y_svr))
                    index_result = pd.date_range(dta.index[d - 2] + datetime.timedelta(hours=1),
                                                 dta.index[d - 2] + datetime.timedelta(hours=25), freq='H')
                    y_result.index = pd.Index(index_result)


                    cursor = db.cursor()
                    sql3 = "INSERT INTO forecastpm25table(name,time,PM25VALUE,cityname) VALUES (%s,%s,%s,%s)"
                    for b in range(0, 25):
                     cursor.execute(sql3, (datas[0].encode(encoding='utf-8'), str(y_result)[0 + 33 * b:19 + 33 * b],str(y_result)[23 + 33 * b:32 + 33 * b], city))

                    db.commit()

                except:
                    # 如果发生错误则回滚
                    db.rollback()

                datas = []



        tempdata.close()
        print('success')
        print(city)
        print(data_time[0])
        db.close()
        time.sleep(3600)  # 自动休眠，每一小时爬一次数据


if __name__ == "__main__":
    file = open('C:/PMdata/citiess.txt','r')#读取城市名字txt，每行为一个名字
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