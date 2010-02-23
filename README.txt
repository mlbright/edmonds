Overview
========

This is a python implementation of Chu-Liu/Edmond's algorithm
to find the minimum spanning tree in a directed graph.

Usage
=====

import edmonds

# Below, g is graph representation of minimum spanning tree
# root is the starting node of the MST, and G is the input graph
g = edmonds.mst(root,G)

References
==========

* http://en.wikipedia.org/wiki/Edmonds's_algorithm
* http://algowiki.net/wiki/index.php/Edmonds's_algorithm (The java code from this is
in the 'doc' directory.)

Build
=====

$ python setup.py sdist
or
$ python setup.py bdist_rpm
