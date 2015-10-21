import scrapy
from scrapy.spiders import Spider
from scrapy.selector import Selector
from scrapy.http import Request
from accident.items import AccidentItem

urls = ["http://accidentdatacenter.com/type/car-auto-automobile"]

#create more pages
def createPages(self,response):
	noPages = 1100
	pages = []
	for n in range(noPages):
		pages.append(Request(url=response.url + '?page='+str(n+1)))
	return pages

class accSpider(Spider):
	name = "acc"
	allowed_domains = ["accidentdatacenter.com"]
	start_urls = urls

	def parse(self,response):
		requests = []

		sel = Selector(response)
		accs = sel.xpath('//article')
		for acc in accs:
			tempurl = acc.xpath('.//div[@class="field field-name-title"]/h2/a/@href').extract()
			acc_url = "http://accidentdatacenter.com"
			for temp in tempurl:
				acc_url += temp.encode('ascii','ignore')
			#print "==================" + acc_url
			yield Request(acc_url, callback=self.acc_parse)
		if response.url.find('?page=') == -1:
			requests += createPages(self,response)
			for request in requests:
				yield request
	
	def acc_parse(self,response):
		sel1 = Selector(response)
		item = AccidentItem()
		road = ""
		roadtemp = sel1.xpath('.//div[@class="field field-name-field-roadway"]/a/text()').extract()
		for r in roadtemp:
			road += r.encode('ascii','ignore')
		item['road'] = road
		city = ""
		citytemp = sel1.xpath('.//div[@class="field field-name-field-city"]/a/text()').extract()
		for c in citytemp:
			city += c.encode('ascii','ignore')
		item['city'] = city
		county = ""
		countytemp = sel1.xpath('.//div[@class="field field-name-field-county"]/a/text()').extract()
		for c in countytemp:
			county += c.encode('ascii','ignore')
		item['county'] = county
		area = ""
		areatemp = sel1.xpath('.//div[@class="field field-name-field-dma"]/a/text()').extract()
		for a in areatemp:
			area += a.encode('ascii','ignore')
		item['area'] = area
		date = ""
		datetemp = sel1.xpath('.//div[@class="field field-name-field-date"]/span/@content').extract()
		for d in datetemp:
			date += d.encode('ascii','ignore')
		item['date'] = date
		item['link'] = response.url
		desctemp = sel1.xpath('.//div[@class="field-item even"]/p/text()').extract()
		desc = ""
		for d in desctemp:
			desc += d.encode('ascii','ignore')
		item['description'] = desc
		return item


