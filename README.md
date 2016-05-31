# clojure-cards
A simple application/library/thing to simulate playing cards. It needs a better name. The idea is to make an unesscessarily complex card shuffler. 

## Usage
You probably have to read the documentation.

It should 
- Shuffle anything that could be called a card.
- Shuffle multiple decks of cards.
- Sort decks of cards
- Shuffle decks of cards in strange ways.
- Deal hands, cards, etc
- Do all of this efficently.

To do:
- Make own, absurdly complex, PRNG.
- - as it turns out, clojure's [unchecked arithmetic is broken for boxed longs](http://dev.clojure.org/jira/browse/CLJ-1832)
- Make a satsifactory datastructure for abstract cards.
- Read, and extract order from, files containing decks of cards.
- Implement sort: Bogobogosort? Bubble sort? Bitonic Mergesort?

## License

Copyright © 2015 Henry Fellows

Distributed under the GNU GPL.
