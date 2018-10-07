import math
import json
from timeit import default_timer as timer

def words_count(word, dataset):
    docs = 0
    for item in dataset:
        if word in item['content']:
            docs += 1
    print("{} -> {}".format(word, docs))
    return docs

arq_out = open("dataset/dataset_IDF_count", 'w')

dataset_arqin = open("dataset/dataset.out").readlines()
dataset = json.loads(dataset_arqin[0])

words_arqin = open("dataset/words_freq_list").readlines()

words = []

for line in words_arqin:
    i = line.find(":")
    word = line[:i]
    words.append(word)

words_docs = {}

for word in words:
    words_docs[word] = words_count(word, dataset)

out_count = open("dataset/count_words", 'w')
out_count.write(json.dumps(words_docs))

for word in words:
    arq_out.write("{}\t".format(word))

arq_out.write("label\n")