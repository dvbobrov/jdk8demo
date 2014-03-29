#!/bin/bash

"${JAVA_HOME}/bin/java" -XX:-RestrictContended -jar build/libs/microbenchmarks.jar ".*${1}.*"
