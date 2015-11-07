import dbquery
import similarity
import toXML


'''
topics = [63,75]
curr_geo = (-70,40)
curr_tag = "0100000010000"
'''

# executable function, input is topic array, client geolocation and 13-bit string tags for client. Output is XML

def main(topics,curr_geo,curr_tag):
	pg = dbquery.PGclient(topics,curr_geo)
	sim = similarity.AccidentSimilarity(pg.topic,curr_tag,pg.distance)
	sim.distance_normalization()
	info = sim.similarity_computing(pg.info)
	result = []
	for i in range(3):
		result.append(info[len(info)-i-1])
	print toXML.ToXML(result)



'''
#example:
main(topics,curr_geo,curr_tag)
'''	
