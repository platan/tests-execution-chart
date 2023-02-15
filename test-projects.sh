#!/bin/sh

set -x

for dir in test-projects/*
do
  if [ -d "$dir" ]; then
    cd "$dir" || exit
    ./gradlew "$@" || exit
    cd - || exit
  fi
done
