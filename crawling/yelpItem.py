from scrapy.item import Field
from scrapy.item import Item

class YelpItem(Item):
	#review content
	content = Field()

	#review date
	date = Field()

	#Reviewer name
	reviewer_name = Field()

	#Reviewer location
	reviewer_location = Field()

	#restaurant rating
	restaurant_rating = Field()

	#restaurant name
	restaurant_name = Field()

	#restaurant id
	biz_id = Field()
