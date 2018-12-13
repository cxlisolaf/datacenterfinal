import networkx as nx
from pylab import show
import matplotlib.pyplot as plt

G=nx.Graph()
#G.add_edges_from([(1,2),(1,3),(1,4),(3,4)])
#G.nodes(data=True)
#>>> [(1, {}), (2, {}), (3, {}), (4, {})]
#G.node[1]['attribute']='value'
#G.nodes(data=True)
#>>> [(1, {'attribute': 'value'}), (2, {}), (3, {}), (4, {})]
fh=open("facebook_combined.txt",'rb')
G=nx.read_edgelist(fh)
fh.close()
#mygraph = nx.read_gml("facebook_combined.txt")

#nx.write_graphml(G,'so.graphml')
nx.draw(G,node_color='b',node_size=100)
plt.show()