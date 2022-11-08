#!/bin/sh

set -x

for dir in test-projects/*
do
  cd "$dir" || exit
  ./gradlew "$@" || exit
  cd - || exit
done
