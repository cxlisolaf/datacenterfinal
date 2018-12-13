import matplotlib
import matplotlib.pyplot as plt
import numpy as np

a = [464203,515572,533906,533039,547640,550250,553277,552243,616953,572541]
b = [1,2,3,4,5,6,7,8,9,10]
c = [1617588,1803642,1637492,1643246,1672056,1746298,1727241,1722450,1739388,1754786]

# Create plots with pre-defined labels.
fig, ax = plt.subplots()

ax.plot(b, a, 'k--',label='IC probability = 0.01')
ax.plot(b, c, 'k:', label='IC probability = 0.1')
plt.ylabel('running time in ms')
plt.xlabel('initial active node numbers')

legend = ax.legend(loc='right', shadow=True, fontsize='x-large')

# Put a nicer background color on the legend.
#legend.get_frame().set_facecolor('c0')

plt.show()

