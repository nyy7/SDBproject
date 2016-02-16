#!/usr/bin/python   
print('Content-type: text/html\r\n\r')
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

__author__ = "yanglingou"
__date__ = "$Nov 5, 2015 8:11:37 PM$"

import psycopg2 #Python PostgreSQL driver
from xml.etree.ElementTree import Element, SubElement, tostring
import json
import urllib
import cgi
import cgitb
cgitb.enable()

def geocode(road,city,state):
    
    httpurl='https://maps.googleapis.com/maps/api/geocode/json?'
    address=road+','+city+','+state
    components='route'
    key='AIzaSyBO8ALsTiK5_4rvejOHePz4OoqF_lCrfCw'
    httpurl=httpurl+urllib.urlencode({'address': address,'components':components,'key':key})
    data = json.load(urllib.urlopen(httpurl))
    userlat=data['results'][0]['geometry']['location']['lat']
    userlng=data['results'][0]['geometry']['location']['lng']
    geo={"ulat":userlat,"ulng":userlng}
    return geo


    
def getmarkers(road,city,state,topics):

    geo=geocode(road,city,state)

    root=Element('Results')
    user_position=SubElement(root,"UserPosition")
    userlng=SubElement(user_position,"UserLng")
    userlng.text=str(geo["ulng"])
    userlat=SubElement(user_position,"UserLat")
    userlat.text=str(geo["ulat"])

    conn=psycopg2.connect(database="accident",user="root",password="Cs6604GIS",host="52.89.116.42",port="5432")
    cur=conn.cursor()
    sql1="select b.pid,b.name,ST_x(c.geocenter::geometry),ST_y(c.geocenter::geometry),c.link from casetopic as a inner join topic13 as b on a.topicid=b.topicid inner join accident13 as c on a.caseid=c.id WHERE "
    sql1=sql1+"ST_DWithin(geocenter::geometry::geography,POINT'("
    sql1=sql1+str(geo["ulng"])+","
    sql1=sql1+str(geo["ulat"])+")'::geometry::geography, "
    sql1=sql1+scope+") "
        
 
    if topics[0]!='NULL':
        cond1="and b.topicid in ("
        for t in topics:
            cond1+=str(t)+","
        cond1+="-1) "
        sql1=sql1+cond1
   
    sql1=sql1+"order by b.pid"
    cur.execute(sql1)
    result=cur.fetchall()
    
    
    
    
    for row in result:
        record=SubElement(root,"record")
        id=SubElement(record,"id")
        id.text=str(row[0])
        name=SubElement(record,"name")
        name.text=str(row[1])
        lng=SubElement(record,"lng")
        lng.text=str(row[2])
        lat=SubElement(record,"lat")
        lat.text=str(row[3])
        urllink=SubElement(record,"url")
        urllink.text=row[4]
       
            
    
        
    print tostring(root)
    cur.close()
    conn.close()
    

if __name__ == "__main__":
    form = cgi.FieldStorage()
    road = form["road"].value
    city = form["city"].value
    state = form["state"].value
    scope = form["scope"].value    
    topics = form["topics"].value
    topics = topics.split(',')
    getmarkers(road,city,state,topics)
