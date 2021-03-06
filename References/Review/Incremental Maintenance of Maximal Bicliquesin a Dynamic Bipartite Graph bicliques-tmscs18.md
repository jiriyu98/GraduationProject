# Incremental Maintenance of Maximal Bicliques in a Dynamic Bipartite Graph

## Core Part

Object: MBE, maximal biclique enumeration.

## Definition

- Change-Sensetive Algorithm, an algorithm for property P on a dynamic graph and its time complexity for enumerating the change in P is linear in the maginitude of change (in P), and polynomial in the size of the input graph and the size of change in the set of edges.

## Symbol

- $\Gamma (u) = \Gamma_G(u)$ denotes the set of vertices adjacent to vertex u in G
- $G-v$ denotes the graph after deleting $v$ and all its adjacent edges from $E(G)$, similar for $G+v$
- $\Delta (G)$ denotes the maximum degree of a vertex in G and $\delta (G)$ denotes the minimum degree of vertex in G
- $\gamma ^{del}(G_1, G_2) = BC(G_1) / BC(G_2)$ called **subsumed bicliques**
- $B(e)$ denotes the set of maximal bicliques containing edge e
- $g(n)$ denotes the maximum number of maximal bicliques possible in an n vertex bipartite graph. A result due to Prisner [33] shows that g(n) ≤ 2n/2, where equality occurs when n is even. CP(k) also satisfies the equality.

<!-- ### Theorem 1

Every bipartite graph with n vertices at most $2^{n\over2}$ maximal bicliques, and the only external(maximal) bipartite graphs are the graphs CP(k). (cocktail-party graph)

### Theorem

For a graph G with n vertices, m edges, maximum degree ∆, and number of maximal bicliques μ, there is an algorithm MineLMBC for enumerating maximal bi- cliques in G with time complexity O(n∆μ) and space complexity O(m + ∆2). -->

## ALGORITHMS FOR MAXIMAL BICLIQUES

Theorem 1 & Theorem 2 both indicate that the new algorithm will be based on them.

### some algorithms used

- MineLMBC

#### BaseLine

- BaselineBC
  - enumerating $BC(G + H)$
- BaselineBC*(not not change-sensitive)
  - $NewBC$ from subgraph $G_H$ of $G+H$
  - for each maximal biclique b, enumerating maximal bicliques in b − H. If a biclique thus enumerated is present in BC(G), it is output as a subsumed biclique.
- DynamicBC(to avoid enumerating any maximal biclique of G that remains maximal in G + H.)
  - NewBC(New bicliques) - actually they did, avoid problems mentioned above
  - DelBC(Subsumed bicliques)

### **Logic Process**

1) We need just to focus on $\cup _{v\in V_H} \Gamma _{G+H}(v)$
2) we note each subsumed maximal biclique b′ in G is a subgraph of at least one new maximal biclique b, and must also be contained in b − H. So 1)get New 2)Get Del from b-H.
3) $BaselineBC^∗$ can enumerate all maximal bicliques in $G_H$ for static graph, each biclique $b$ thus generated is a new maximal biclique, if b contains at least an edge from $H$. Meanwhile, we also notice that each subsumed maximal $b'$ graph bicliques's in G is a subgraph of at least one new maximal biclique $b$ and must also be contained $b-H$. So, If a biclique thus enumerated is present in BC(G), it is output as a subsumed biclique. However, the $BaselineBC*$ is not Change-Sensetive algorithm because it enumerates all new $BC$(i.e. $\gamma (G1, G2)$).
4) $NewBC$ and $SubBC$, there are some lemmas & theories.

## Experiment

They create the initial graphs (epinions-rating-init, lastfm-song-init, movielens-10M-init, and wiktionary-init) by removing all the edges from the original graphs and present the edge stream in the increasing order of their ***time-stamps***.
