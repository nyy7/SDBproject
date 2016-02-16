import dbquery
import similarity
import toXML
import reportdb
import toBit
import geocoding

'''
topics = [63,75]
curr_geo = (-70,40)
curr_tag = "0100000010000"
'''

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
    road = sys.argv[1]
    city = sys.argv[2]
    state = sys.argv[3]    
    time = sys.argv[4]
    topics = sys.argv[5]
    topics = topic.split(',')
    main(road,city,state,time,topics)

#example:
#main("I66","Fairfax","VA","2015-11-18 13:33:00",[63])

