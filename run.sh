#!/bin/bash

cd src/server/img

node ../index.js & python3 -m http.server 5555 && fg
