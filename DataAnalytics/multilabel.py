import psycopg2
import numpy as np
from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import CountVectorizer,TfidfTransformer
from sklearn.svm import LinearSVC
from sklearn.multiclass import OneVsRestClassifier

class AutoLabeling:
	def __init__(self):
		conn = psycopg2.connect(database = "accident", user="root",password="Cs6604GIS",host="52.89.116.42",port="5432")
		cur = conn.cursor()
		print "successful connected"
		# import labeled data
		sql_selectlabeled = "SELECT * FROM labeled"
		cur.execute(sql_selectlabeled)
		data_train = cur.fetchall()
		self.id_train = []
		self.X_train = []
		self.y_train = []
		for row in data_train:
			self.id_train.append(row[0])
			self.X_train.append(row[1])
			self.y_train.append(row[2])

		# import unlabeled data
		sql_selectunlabeled = "SELECT * FROM unlabeled"
		cur.execute(sql_selectunlabeled)
		data_test = cur.fetchall()
		self.id_test = []
		self.X_test = []
		for row in data_test:
			self.id_test.append(row[0])
			self.X_test.append(row[1])
		print "obtained data"


	def multilabel(self):
		print "labeling begin"
		classifier = Pipeline([\
				('vectorizer', CountVectorizer((1,2))),\
				('tfidf', TfidfTransformer()),\
				('clf', OneVsRestClassifier(LinearSVC()))])
		classifier.fit(self.X_train, self.y_train)
		predicted = classifier.predict(self.X_test)

		'''
		for item, labels in zip(X_test, predicted):
			print '%s => %s' % (item, ', '.join(predicted[x] for x in labels) )
		'''
		print "end labeling"	
		return predicted

	def insertLabel(self,predicted):
		print "start insertion"
		conn = psycopg2.connect(database = "accident", user="root",password="Cs6604GIS",host="52.89.116.42",port="5432")
		cur = conn.cursor()
	
		for i in range(len(predicted)):
			sql_insert = ""
			sql_insert = "UPDATE unlabeled set bit13=B\'"+str(predicted[i])+"\' WHERE pid=" + str(self.id_test[i])
			print sql_insert
			cur.execute(sql_insert)
			conn.commit()

		if conn:
			conn.close()


lb = AutoLabeling()
predict = lb.multilabel()
lb.insertLabel(predict)

