import urllib2
import json



def geocode(road,city,state):
	url = "https://maps.googleapis.com/maps/api/geocode/json?address="
	address = road +"," +city+","+state
	address=address.decode('utf-8','ignore')
	url += address
	route = "route"
	route = route.decode('utf-8','ignore')
	url += "&component="+route
	key = "AIzaSyBO8ALsTiK5_4rvejOHePz4OoqF_lCrfCw"
	key=key.decode('utf-8','ignore')
	url += "&key="+key
	
	jsonfile = urllib2.urlopen(url)
	data = json.load(jsonfile)
	#print data["results"][0]
	location =  data["results"][0]["geometry"]["location"]
	lat = location["lat"]
	lng = location["lng"]
	result = (lng,lat)
	return result

#print geocode("I66","Fairfax","VA")
