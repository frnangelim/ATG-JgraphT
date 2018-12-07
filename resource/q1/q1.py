#!/usr/bin/env python
# -*- coding: utf-8 -*-

# Pergunta: Qual o álbum que está mais presente nas playlists? 

import json
import csv

with open('../spotifyDB.json') as f:
	data = json.load(f)

charset='utf-8'
nodes = []
edges = []

def setNodes():
	global nodes
	for p in data["playlists"]:
		for t in p["tracks"]:
			nodes.append(t)

def setEdges():
	global edges
	for i in range(len(nodes)):
		track1 = nodes[i]
		for j in range(i+1, len(nodes)):
			track2 = nodes[j]
			if(track1["album_uri"] == track2["album_uri"]):
				edge = [track1["track_uri"], track2["track_uri"]]
				edges.append(edge)

def generateCSV():
	with open('nodes.csv', mode='w') as nodes_file:
		nodes_writer = csv.writer(nodes_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
		nodes_writer.writerow(['Id', 'Label','Album'])
		for node in nodes:
			row = [ node["track_uri"], node["track_name"].encode(charset), node["album_name"].encode(charset) ]
			nodes_writer.writerow(row)

	with open('edges.csv', mode='w') as edges_file:
		edges_writer = csv.writer(edges_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
		edges_writer.writerow(['Source', 'Target'])
		for edge in edges:
			edges_writer.writerow(edge)

def run():
	setNodes()
	setEdges()
	generateCSV()

run()