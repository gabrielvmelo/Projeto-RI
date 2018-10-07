import json
import math
from timeit import default_timer as timer
import pandas as pd

arq_out = open("dataset_TF", 'w')

dataset_arqin = open("dataset/dataset.out").readlines()
dataset = json.loads(dataset_arqin[0])

words_arqin = open("dataset/words_freq_list").readlines()

words = []

for line in words_arqin:
    i = line.find(":")
    word = line[:i]
    words.append(word)

for word in words:
    arq_out.write("{}\t".format(word))

arq_out.write("label\n")

start = timer()
print(start)

for item in dataset:
    for word in words:
        freq = item['content'].count(word)

        if freq > 0:
            tf = 1 + math.log2(freq)
        else:
            tf = 0
        
        arq_out.write("{}\t".format(tf))
    arq_out.write("{}\n".format(item['label']))

total = timer() - start

print(total)

'''
print(len(words))
start = timer()

for word in words:
    a = dataset[4]['content'].count(word)
    print("{}: {} ".format(word, a))

total =  timer() - start
print(total)'''