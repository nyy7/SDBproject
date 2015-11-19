import psycopg2

def report_accident(road,city,state,bitstring,time,geo):
	conn = psycopg2.connect(database = "accident", user="root",password="Cs6604GIS",host="52.89.116.42",port="5432")
	cur = conn.cursor()
	city_state = city+","+state
	print city_state
	sql_report = "INSERT INTO report(road,city,topic,time,geocenter) VALUES(\'"+road+"\',\'"+city_state+"\',B\'"+bitstring+"\',timestamp\'"+time+"\',POINT"+str(geo)+");"
	print sql_report
	cur.execute(sql_report)
	conn.commit()

	if conn:
		conn.close()

#report_accident("50","Chantilly","VA","0010000100000","2015-11-17 22:10:00",(-40,50))

