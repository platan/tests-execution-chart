#!/bin/sh

set -x

for dir in examples/*
do
  cd "$dir" || exit
  ./gradlew "$@" || exit
  cd - || exit
done
