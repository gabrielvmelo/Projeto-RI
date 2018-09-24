import requests

domains = ['IMDB', 'Rotten Tomatoes', 'TMDB', 'TV Guide', 'TvTrack', 'Metacritic']


total = 0

for site in domains:
    i = 0
    links = open("dataset/{}/{}_list_pos.txt".format(site, site)).readlines()
    print("Downloading... {}".format(site))
    for link in links:
        link = link.replace("\n", "")
        print(link)
        c = requests.get(link)
        name = "dataset/{}/{}_{}_pos.html".format(site, i, site)
        arqout = open(name, 'wb').write(c.content)
        i += 1
        total += 1
        print("Got it... {}: {}, Total: {}".format(site, i, total))


for site in domains:
    i = 0
    links = open("dataset/{}/{}_list_neg.txt".format(site, site)).readlines()
    print("Downloading... {}".format(site))
    for link in links:
        link = link.replace("\n", "")
        print(link)
        c = requests.get(link)
        name = "dataset/{}/{}_{}_neg.html".format(site, i, site)
        arqout = open(name, 'wb').write(c.content)
        i += 1
        total += 1
        print("Got it... {}: {}, Total: {}".format(site, i, total))