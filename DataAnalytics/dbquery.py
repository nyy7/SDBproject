import psycopg2

class PGclient:
	def __init__(self,topics,geo):
		conn = psycopg2.connect(database = "accident", user="root",password="Cs6604GIS", host="52.89.116.42", port="5432")
		#print "open database succesfully"
		cur = conn.cursor()
		sql_select = "SELECT DISTINCT id,road,city,link,timestamp,bit13,ST_Distance_Sphere(geocenter::geometry,"
		sql_select += "point"+str(geo)+"::geometry)"
		sql_from = " FROM accident13, casetopic WHERE id=caseid and geocenter is not null and (topicid = "+str(topics[0])
		
		for i in range(len(topics) - 1):
			sql_from += " OR topicid = "+str(topics[i+1])
		sql_from += ")"
		sql = sql_select + sql_from
		#print sql

		cur.execute(sql)		
		matchdata = cur.fetchall()
		#print self.matchdata[0][4]
		#print len(matchdata[0])
		self.info = []
		self.topic = []
		self.distance = []
		for row in matchdata:
			self.info.append(row[:5])
			self.topic.append(row[5])
			self.distance.append(row[6])
		if conn:
			conn.close()

'''
pg = PGclient([63,75],(-70,40))
print pg.topic[:3]
'''
		
