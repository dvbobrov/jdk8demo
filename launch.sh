#!/bin/bash

~/dev-tools/jdk1.8.0/bin/java -XX:-RestrictContended -jar build/libs/microbenchmarks.jar ".*${1}.*"
