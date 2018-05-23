#!/bin/bash

javac megherat/adam/Main.java 
java megherat.adam.Main 
find . -type f -path "./megherat/adam/*" -name "*.class" -exec rm -f {} \;
