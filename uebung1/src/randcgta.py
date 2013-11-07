#!/usr/bin/env python3
import random

chars = 'cgat'

for i in range(5,50):
    s = ''
    for j in range(i):
        s += str(chars[random.randint(0,3)])
    print('> sinnlos')
    print(s)
