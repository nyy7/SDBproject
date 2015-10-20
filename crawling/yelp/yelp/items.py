# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class YelpItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
	
	#review content
	content = scrapy.Field()

	#review date
	date = scrapy.Field()

	#Reviewer name
	reviewer_name = scrapy.Field()

	#Reviewer location
	reviewer_location = scrapy.Field()

	#restaurant rating
	restaurant_rating = scrapy.Field()

	#restaurant name
	restaurant_name = scrapy.Field()

	#restaurant id
	biz_id = scrapy.Field()

	#restaurant url
	url = scrapy.Field()
