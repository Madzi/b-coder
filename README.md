# Bencoding

Bencoding is a way to specify and organize data in a terse format. It supports the following types: byte strings, 
integers, lists, and dictionaries. 

## Encoding algorithm

Bencode uses **ASCII** characters as delimiters and digits.

* An integer is encoded as **i**`<contents>`**e**. Leading zeros are not allowed (although the number zero is still 
represented as "0"). Negative values are encoded by prefixing the the number with a minus sign. The number 42 would 
this be encoded as `i42e`, 0 as `i0e`, and -42 as `i-42e`. Negative zero is not permitted.

* A byte string (a sequence of bytes, not necessarily characters) is encoded as `<length>:<contentns>`. The length is 
encoded in base 10, like integers, but must be non-negative (zero is allowed); the contents are just the bytes that 
make up the string. The string "spam" would be encoded as `4:spam`. The specification does not deal with encoding of 
characters outside the ASCII set; to mitigate this, some BitTorrent applications explicitly communicate the encoding 
(most commonly UTF-8) in various non-standard ways. This is idendical to how netstrings work, except that netstrings 
additionally append a comma suffix after the byte sequence.

* A list of values is encoded as **l**`<contents>`**e**. The contents consits of the bencoded elements of the list, in 
order, concatenated. A list consiting of the string "spam" and the number 42 would be encoded as: `l4:spami42ee`. Note 
the absence of separators between elements.

* A dictionary is encoded as **d**`<contents>`**e**. The elements of the dictionary are encoded each key immediately 
followed by its value. All keys must be byte strings and must appear in lexicographical order. A dictionary that 
associates the values 42 and  "spam" with the keys "foo" and "bar", respectively (in other words, 
`{"bar":"spam", "foo":42}`), would be encoded as follows: `d3:bar4:spam3:fooi42ee.

There are no restrictions on what kind of values may be stored in lists and dictionaries; they may (and usually do) 
contain other lists and dictionaries. This allows for arbitrarily complex data structures to be encoded.