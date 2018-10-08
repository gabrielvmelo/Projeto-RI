import json

arq_in = open("dataset/dataset_TF_droped")
arq_idf = open("dataset/dataset_IDF_droped").readlines()[1].split("\t")

#print(arq_idf)

weights = []

arq_out = open("dataset/dataset_TF_IDF_ok", 'w')
arq_TF = arq_in.readline()
arq_out.write(arq_TF)

for i in range(220):
    print(i)
    line = arq_in.readline().split("\t")
    for j in range(len(line)):
        result = float(line[j]) * float(arq_idf[j])
        arq_out.write("{}\t".format(result))
    arq_out.write("\n")
