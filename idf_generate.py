import json
import math

total = 220

arq_out = open("dataset/dataset_IDF", 'w')
arq_in = open("dataset/count_words").readlines()

idf = json.loads(arq_in[0])

for word in idf:
    if idf[word] > 0:
        idf[word] = math.log2(220/idf[word])
    print("{} -> {}".format(word, idf[word]))

arq_out.write(json.dumps(idf))