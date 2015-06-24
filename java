#!/bin/bash

# *************************************************
# java-algs4
# Hayk Martirosyan
# -------------------
# Wrapper for java that includes algs4 libraries.
# *************************************************

# This must match the install directory
INSTALL=$( dirname "${BASH_SOURCE[0]}" )/lib

# Sets the path to the classpath libraries
jars=(.:${INSTALL}/stdlib.jar:${INSTALL}/algs4.jar)

java -cp "$jars" "$@"
