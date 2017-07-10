# coding:utf-8
import threading
import urllib.request
import re,sys
import time
import hashlib
import os,json
import pymysql
import datetime
sys.setdefaultencoding = 'utf-8'

def fetchdata(city):
    print(city)
    while True:
        url ='https://free-api.heweather.com/v5/weather?city='+city+'&key=97c2562a4e2746e381e2c2efd877b050'

        req = urllib.request.Request(url)

        resp = urllib.request.urlopen(req).read()
        json_data = json.loads(resp)
        data = json_data['HeWeather5'][0]
         
        prov = data['basic']['city']
        updatetime = data['basic']['update']['loc']
        updatetime = updatetime+":00"
        weather = data['now']['cond']['txt']
        api = data['aqi']['city']['aqi']

        qlty = data['aqi']['city']['qlty']

        pm25 = data['aqi']['city']['pm25']
        pm10 = data['aqi']['city']['pm10']
        co = data['aqi']['city']['co']
        no2 = data['aqi']['city']['no2']
        o3 = data['aqi']['city']['o3']
        so2 = data['aqi']['city']['so2']

        weather_forcast_txt = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s"%(prov,updatetime,api,qlty,weather,pm25,pm10,co,no2,o3,so2)

        print(weather_forcast_txt)
        print(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())))#显示当前时间

        db = pymysql.connect("localhost", "root", "HHU123", "pm25",  charset='utf8' )
        print('连接成功')
        try:
         cursor = db.cursor()
         sql = "INSERT INTO pm25table(name,time,AQI,AQIKind,weather,PM25VALUE,PM10VALUE,COVALUE,NO2VALUE,O3VALUE,SO2VALUE,cityname) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
         cursor.execute(sql, (prov.encode(encoding='utf-8'), updatetime, api, qlty, weather, pm25, pm10,co, no2, o3, so2, city))
         db.commit()
        except Exception as  E:
         print(E)
        
        time.sleep(3600)#自动休眠，每1个时爬一次数据  

if __name__ == "__main__":
    file = open('C:/PMdata/citiess.txt','r')#读取城市名字txt，每行为一个   名字
    lines= file.readlines()
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
