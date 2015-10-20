#!usr/bin/env python

import twitter

CONSUMER_KEY = "W3Do8ZJFynpLcwko38T5uefrp"
CONSUMER_SECRET = "aeV46l71dTNeDmeX4SZ4aZ0LAlWX8hn5y2A2FAN1whjkJLaaUy"

OAUTH_TOKEN = "158224205-VAkUtPsFWIc742lPYYjQQfXehbiV04u9qOPJX2NV"
OAUTH_TOKEN_SECRET = "vy1x8v5ccghCUMNLZZV5TpIB5aW9XiRLfYrQDfEKRi4zu"

#Setting up Twitter API
api = twitter.Api(CONSUMER_KEY,CONSUMER_SECRET,OAUTH_TOKEN,OAUTH_TOKEN_SECRET)
search = api.GetSearch(term='diarrhea',geocode=(38.8998831,-77.0516454,'20mi'),count='200')
for t in search:
	print t.user.screen_name + '\n' + t.text + '\n'

