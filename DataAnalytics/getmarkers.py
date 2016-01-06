#!/usr/bin/python   
print('Content-type: text/html\r\n\r')


__author__ = "yanglingou"
__date__ = "$Nov 13, 2015 5:16:38 PM$"

import psycopg2 #Python PostgreSQL driver
from xml.etree.ElementTree import Element, SubElement, tostring


    
def getmarkers():
    sql="select ST_x(geocenter::geometry),ST_y(geocenter::geometry), bit13 from accident13 where timestamp between '2015-10-18 07:00:00+00' and '2015-10-25 07:00:00+00'"
    conn=psycopg2.connect(database="accident",user="root",password="Cs6604GIS",host="52.89.116.42",port="5432")
    cur=conn.cursor()
    cur.execute(sql)
    result=cur.fetchall()
    root=Element('Results')
    for row in result:
        record=SubElement(root,"record")
        lng=SubElement(record,"lng")
        lng.text=str(row[0])
        lat=SubElement(record,"lat")
        lat.text=str(row[1])
        topic=SubElement(record,"topic")
        topic.text=row[2]
    print tostring(root) 
    cur.close()
    conn.close()
    

if __name__ == "__main__":
    getmarkers()

