import requests
import json

domains = ['IMDB', 'Rotten Tomatoes', 'TMDB', 'TV Guide', 'TvTrack', 'Metacritic']
arqs = ['pos', 'neg']
base = []
total = 0

for site in domains:
    for arq in arqs:
        links = open("dataset/{}/{}_list_{}.txt".format(site, site, arq)).readlines()
        print("Downloading... {}".format(site))
        print()
        for link in links:
            link = link.replace("\n", "")
            print("Link: {}".format(link))
            r = requests.get(link)
            dic = {}
            dic['url'] = link
            dic['content'] = str(r.content)
            if arq == "neg":
                dic['label'] = 0
            else:
                dic['label'] = 1
            
            base.append(dic)

            total += 1
            print("Got it... Total: {}".format(total))

arqout = open("dataset/dataset.out", 'w')
arqout.write(json.dumps(base))
arqout.close()
