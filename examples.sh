#!/bin/sh

set -x

for dir in examples/*
do
  cd "$dir" || exit
  ./gradlew "$@"
  cd - || exit
done
