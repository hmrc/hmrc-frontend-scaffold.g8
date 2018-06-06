#!/bin/bash

#Run this once before using sbt so that your new project is added to git - without this you will get an error from our sbt build

git init && git add . && git commit -m "Initial version"
