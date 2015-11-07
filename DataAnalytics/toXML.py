from xml.etree.ElementTree import Element, SubElement, tostring
import similarity

def ToXML(matchdata):
	root = Element('RecommendAccidents')
	for row in matchdata:
		accident = SubElement(root,"Accident")
		id = SubElement(accident,"ID")
		id.text = str(row[0][0])
		road = SubElement(accident,"ROAD")
		road.text = row[0][1]
		city = SubElement(accident,"CITY")
		city.text = row[0][2]
		url = SubElement(accident,"LINK")
		url.text = row[0][3]
		similarity = SubElement(accident,"SIMILARITY")
		similarity.text = str(row[1])
	print tostring(root)



'''
data = similarity.test()
ToXML(data)
'''
