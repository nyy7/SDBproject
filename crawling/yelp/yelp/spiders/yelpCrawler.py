
import scrapy
from scrapy.spiders import Spider
from scrapy.selector import Selector
from scrapy.http import Request
from yelp.items import YelpItem

urls = ["http://www.yelp.com/search?find_desc=restaurants&findloc=washington+dc"]

def createPages(self,response):
	restaurantsPerPage=10
	sel = Selector(response)
	totalRestaurants = 20
	pages = [Request(url=response.url + '?start='+str(restaurantsPerPage*(n+1)),callback=self.parse)\
			for n in range(totalResturants/restaurantsPerPage)]
	return pages

def createRestaurantPageLinks(self,response):
	reviewsPerPage = 40
	sel = Selector(response)
	totalReviews = int(sel.xpath('//div[@class="rating-info clearfix"]//span[@itemprop="reviewCount"]/text()').extract()[0].strip().split(' ')[0])
	print "total reviews:", totalReviews
	pages = [Request(url=response.url + '?start=' + str(reviewsPerPage*(n+1)),callback=self.parse)\
			for n in range(totalReviews/reviewsPerPage)]
	return pages

class YelpSpider(Spider):
	name = "yelp"
	allowed_domains=["yelp.com"]
	start_urls = urls

	def parse(self,response):
		sel = Selector(response)
		restaurants = sel.xpath('//div[@class=search-result natural-search-result]')
		for res in restaurants:
			tempurl = res.xpath('.//span[@class=indexed-biz-name]/a/@href').extract()
			res_url = "http://www.yelp.com"
			for temp in tempurl:
				res_url += temp.encode('ascii','ignore')
			yield Request(res_url, callback=self.review_parse)

		if response.url.find('?start=') == -1:
			requests += createPages(self,response)
			for request in requests:
				yield request

				
	def review_parse(self,response):
		reviews = response.xpath('//div[@class="review review--with-sidebar"]')
		for review in reviews:
			item = YelpItem()
			item['restaurant_name'] = sel.xpath('//meta[@property="og:title"]/@content').extract()
			item['reviewer_name'] = review.xpath('.//li[@class="user-name"]/a/text()').extract()
			item['date'] = review.xpath('.//meta[@itemprop="datePublished"]/@content').extract()
			ucontent = review.xpath('.//p[@itemprop="description"]/text()').extract()
			content = ""
			for uni in ucontent:
				asc = uni.encode('ascii','ignore')
				content += asc
			item['content'] = content
			item['url'] = response.url
			if "diarrhea" in content:
				yield item
			elif "vomitting" in content:
				yield item


		if response.url.find('?start=') == -1:
			requests += createRestaurantPageLinks(self, response)
			for request in requests:
				yield request


