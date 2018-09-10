<h1 align="center">Simple ciphers</h1>
<p align="center"><img src="https://i.imgur.com/ZfMSTrk.png" width=150></p>

<p align="center">
<a href="https://github.com/N1ghtF1re/Simple-ciphers/stargazers"><img src="https://img.shields.io/github/stars/N1ghtF1re/Simple-ciphers.svg" alt="Stars"></a>
<a href="https://github.com/N1ghtF1re/Simple-ciphers/releases"><img src="https://img.shields.io/badge/downloads-6-brightgreen.svg" alt="Total Downloads"></a>
<a href="https://github.com/N1ghtF1re/Simple-ciphers/releases"><img src="https://img.shields.io/github/tag/N1ghtF1re/Simple-ciphers.svg" alt="Latest Stable Version"></a>
<a href="https://github.com/N1ghtF1re/Simple-ciphers/blob/master/LICENSE"><img src="https://img.shields.io/github/license/N1ghtF1re/Simple-ciphers.svg" alt="License"></a>
</p>
</p>

## About the library
The library contains three simple ciphers: rail-fence, rotating square and wizner cipher

## Class RailFence: 
Methods: 
- encode(String message, int key) - return encoded message with a cipher "Rail-Fence" with the specified key.
- decode(String message, int key) - return decoded message with a cipher "Rail-Fence" with the specified key.

## Class VigenerCipher: 
Constructors: 
- VigenerCipher() - default, RUSSIAN ALPHABET
- VigenerCipher(String aplhaber) - With the assignment the alphabet. The characters are written consecutively by a string. Example: "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"

Methods: 
- encode(String message, str keyword) - return encoded message with a Vigener cipher with the specified keyword.
- decode(String message, str keyword) - return decoded message with a Vigener cipher with the specified keyword.


## Class RotatingSquare: 
Constructors: 
- RotatingSquare() - default, used default key.
- RotatingSquare(int[][] key) - With the assignment the key, key - the array of "holes" in the square of the format [ [x,y], [x,y], ..]. Example: {{0,0}, {3,1}, {2,2}, {1,3}}

Methods: 
- encode(String message) - return encoded message with a cipher "Rotating Square".
- decode(String message) - return decoded message with a cipher "Rotating Square".
