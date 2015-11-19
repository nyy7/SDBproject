


def topicToBit(topicList):
	topics = [63,80,10,76,20,9,79,72,31,98,85,36,75]
	bitList = ['0','0','0','0','0','0','0','0','0','0','0','0','0']
	for i in topicList:
		bitList[topics.index(i)]='1'
	bitstring = ''.join(bitList)
	return bitstring

#print topicToBit([63,36,79,20])

	
