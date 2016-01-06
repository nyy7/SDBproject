#!/usr/bin/python   
print('Content-type: text/html\r\n\r')
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

__author__ = "yanglingou"
__date__ = "$Nov 5, 2015 8:11:37 PM$"

import psycopg2 #Python PostgreSQL driver
from xml.etree.ElementTree import Element, SubElement, tostring

def getmarkers():
    conn=psycopg2.connect(database="accident",user="root",password="Cs6604GIS",host="52.89.116.42",port="5432")
    cur=conn.cursor()
    sql1="select ST_x(c.geocenter::geometry),ST_y(c.geocenter::geometry) from casetopic as a inner join topic13 as b on a.topicid=b.topicid inner join accident13 as c on a.caseid=c.id WHERE b.pid=1 and timestamp between \'2015-01-01 00:00:00\' and \'2015-04-01 00:00:00\'"    
    cur.execute(sql1)
    result=cur.fetchall()
    root=Element('Results')
    for row in result:
        record=SubElement(root,"record")
        lng=SubElement(record,"lng")
        lng.text=str(row[0])
        lat=SubElement(record,"lat")
        lat.text=str(row[1])        
    print tostring(root)
    
    cur.close()
    conn.close()

if __name__ == "__main__":
    getmarkers()

