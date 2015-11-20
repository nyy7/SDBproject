# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class AccidentItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
	road = scrapy.Field()
	city = scrapy.Field()
	county = scrapy.Field()
	area = scrapy.Field()
	date = scrapy.Field()
	link = scrapy.Field()
	description = scrapy.Field()
