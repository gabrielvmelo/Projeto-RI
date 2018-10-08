import json
import math
import pandas as pd

dataset = pd.read_csv("dataset/dataset_TF", sep="\t")
idf = json.loads(open("dataset/dataset_IDF").readlines()[0])

#dataset.to_csv("dataset/teste", sep="\t")
idf_zero = []
idf_list = []

for word in idf:
    if idf[word] == 0:
        idf_zero.append(word)
    else:
        idf_list.append(word)
print(len(idf_zero))

dataset = dataset.drop(columns = idf_zero)
dataset.to_csv("dataset/datset_drop_TF", sep='\t', index=False)

idf_dataset = pd.DataFrame.from_dict(idf)

'''l = len(dataset)

for i in range(l):
    print(i)
    aux = 0
    for word in idf_list:
        aux += 1
        print(aux)
        dataset.at[i, word] = dataset.iloc[i][word] * idf[word]

dataset.to_csv("dataset/dataset_TFxIDF", sep='\t')'''