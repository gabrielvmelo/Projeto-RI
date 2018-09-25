import json
from sklearn.feature_extraction.text import CountVectorizer

arqin = open("dataset/dataset.out").readlines()
dataset = json.loads(arqin[0])
print(len(dataset))

word = []
freq = {}

txt = dataset[4]['content'].split("\n")
print(type(txt))
vectorizer = CountVectorizer()

print( vectorizer.fit_transform(list(txt)).todense() )
print( vectorizer.vocabulary_ )