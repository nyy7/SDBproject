from math import *
import numpy as np

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
	def __init__(self, geos, tags, curr_geo, curr_tag):
		self.tags = tags
		self.curr_tag = curr_tag
		dist_temp = []
		for tup in geos:
			dist_temp.append(sqrt(pow(tup[1]-curr_geo[1],2)\
					pow(tup[2]-curr_geo[2],2)))
		self.dists = dist_temp


	# normalize the distance array, self.dists
	def distance_normalization(self):
		#transform to np array
		dists = np.array(self.dists)
		# mean center the data
		self.dists -= np.mean(dists,axis=0)
		# divide by the standard deviation
		self.dists /= np.std(dists,axis=0) 
	

# this function is to calculate the similarity between current accident and selected accidents
## arrTag is an array containing bitstrings for selected candidates
## arrayD is a array containing normalized distances from historical acc to current acc
## s is the tag bit string for current acc
	def similarity_computing(self):
		similarity = []
		curr_tag = strToList(self.curr_tag)
		for i in range(len(self.tags)):
			tag = strToList(self.tags[i])
			sim = hamming_distance(tag,curr_tag)
			dist = self.dists[i]
		# add distance weight to result
			result = sim - 0.9 * dist
			similarity.append(result)
		return similarity






