import json
from sklearn.feature_extraction.text import CountVectorizer

arqin = open("dataset/dataset.out").readlines()
dataset = json.loads(arqin[0])
print(len(dataset))
txts = []
word = []
freq = {}

'''
txt = dataset[4]['content'].split("\n")
print(type(txt))

'''
i = 0
for elem in dataset:
    aux = elem['content'].split("\n")
    for t in aux:
        txts.append(t)
    i += 1
    print(i)
vectorizer = CountVectorizer()
print(vectorizer.fit_transform(list(txts)).todense())
print(vectorizer.vocabulary_)
print(len(vectorizer.vocabulary_))
print(type(vectorizer.vocabulary_))
arqout = open("dataset/words_freq_list", 'w')

order_vocabulary = sorted(vectorizer.vocabulary_.items(), key = lambda kv : kv[1])
print(type(order_vocabulary))
for elem in order_vocabulary:
    arqout.write("{}: {}\n".format(elem[0], elem[1]))