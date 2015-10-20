import scrapy, time, sys, os
from scrapy.spiders import Spider
from scrapy.selector import Selector
from scrapy.http import Request
from reslink.items import ReslinkItem

urls = ["http://www.yelp.com/search?find_desc=restaurants&find_loc=Washington,+DC"]

#create more pages for one search page
def createPages(self,response):
	restaurantsPerPage=10
	sel = Selector(response)
	totalRestaurants = 50
	pages = []
	for n in range(totalRestaurants/restaurantsPerPage):
		url_test = response.url + '?start='+str(restaurantsPerPage*(n+1))
#		os.system('wget ' + url_test)
		pages.append(Request(url=url_test, callback=self.parse))

		print '================' + url_test
	return pages

#crawl restaurnts links from search pages
class ResLinkSpider(Spider):
	name = "findRestaurants"
	allowed_domains = ["yelp.com"]
	start_urls = urls

	def parse(self,response):
		requests = []

		ts = str(time.time())
		f = open('yelp-rests-' + ts + '.html', 'w')
		f.write(response.body)
		f.close()
		sel = Selector(response)
		restaurants = sel.xpath('//div[@class="search-result natural-search-result"]')

		for res in restaurants:
			item = ReslinkItem()
			tempurl = res.xpath('.//span[@class="indexed-biz-name"]/a/@href').extract()
			ur = "http://www.yelp.com"
			for temp in tempurl:
				ur += temp.encode('ascii','ignore')
			item['url'] = ur
			#item['name'] = res.xpath('.//span[@class="indexed-biz-name"]/a/text()').extract
			yield item
	
		if response.url.find('?start=') == -1:
			requests += createPages(self,response)
			for request in requests:
				yield request
