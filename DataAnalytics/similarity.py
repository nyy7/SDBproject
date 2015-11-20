from math import *
import numpy as np
import operator
import dbquery

# convert string to list of integer
def strToList(s):
	list = []
	for c in s:
		list.append(int(c))
	return list

# calculate the similarity between two bit string
## hamming distance
def hamming_distance(x,y):
	return sum(a != b for a, b in zip(x,y))

## euclidean distance
def euclidean_distance(x,y):
	return sqrt(sum(pow(a-b,2) for a, b in zip(x,y)))

## cosine similarity
def square_rooted(x):
	return round(sqrt(sum(a*a for a in x)),3)

def cosine_similarity(x,y):
	numerator = sum(a * b for a, b in zip(x,y))
	denominator = square_rooted(x)*square_rooted(y)
	return round(numerator/float(denominator),3)

'''
## examples
x = "1000111"
y = "1001111"
x = strToList(x)
y = strToList(y)

print "hamming distance:", hamming_distance(x,y)
print "euclidean distance:", euclidean_distance(x,y)
print "cosine similarity:", cosine_similarity(x,y)
'''



# this class is for compare current accident with selected historical accidents
## input is the geolocations for selected historical accidents, geos;
## geolocations for current accident, curr_geo;
## the tag bitstring for selected historical accidents, tags;
## the tag bitstring for current accident, curr_tag
class AccidentSimilarity:
	def __init__(self, tags, curr_tag, distance):
		self.tags = tags
		self.curr_tag = curr_tag
		hamming=[]
		for tag in tags:
			tagList = strToList(tag)
			diff = hamming_distance(tag,curr_tag)
			hamming.append(diff)
		self.dists = self.distance_normalization(distance)
		self.hamming = self.distance_normalization(hamming)

	# normalize the distance array, self.dists
	def distance_normalization(self,d):
		#transform to np array
		dists = np.array(d)
		# mean center the data
		d -= np.mean(dists,axis=0)
		# divide by the standard deviation
		d /= np.std(dists,axis=0) 
		return d
	

# this function is to calculate the similarity between current accident and selected accidents
	def similarity_computing(self,info):
		results = {}
		
		for i in range(len(self.dists)):
			diff = self.hamming[i]
			dist = self.dists[i]
			sim = -(diff + 0.9 * dist)
			
			key_index = info[i]
			results[key_index] = sim
		# sort info by similarity
		sorted_info = sorted(results.items(), key=operator.itemgetter(1))
		return sorted_info



'''
# test AccidentSimilarity
tags = ["1010","0110","0011","1000"]
distance = [10,20,25,10]
curr_tag = "0100"
info = [(1,"haha"),(2,"xiix"),(3,"haha"),(4,"xixi")]

sim1 = AccidentSimilarity(tags,curr_tag,distance)
sorted_info = sim1.similarity_computing(info)
print sorted_info[-3:]
'''


