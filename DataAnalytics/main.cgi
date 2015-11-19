#!/usr/bin/python
print('Content-type: text/html\r\n\r')

import dbquery
import similarity
import toXML
import reportdb
import toBit
import geocoding
cgitb.enable()

# executable function, input is topic array, client geolocation and 13-bit string tags for client. Output is XML

def main(road,city,state,time,topics):
	#convert address to geolocation
	curr_geo = geocoding.geocode(road,city,state)
	
	#convert topic list to bitstring
	curr_tag = toBit.topicToBit(topics) 
	

	#insert report accident to database
	reportdb.report_accident(road,city,state,curr_tag,time,curr_geo)

	pg = dbquery.PGclient(topics,curr_geo)
	sim = similarity.AccidentSimilarity(pg.topic,curr_tag,pg.distance)
	info = sim.similarity_computing(pg.info)
	results = []
	for i in range(3):
		results.append(info[len(info)-i-1])
		#print info[len(info)-i-1]
	print toXML.ToXML(results)



if __name__=="__main__":
	form = cgi.FieldStorage()
	road = form["road"].value
	city = form["city"].value
	state = form["state"].value
	time = form["time"].value
	topics = form["topics"].value
	topics = topics.split(',')
	main(road,city,state,time,topics)

