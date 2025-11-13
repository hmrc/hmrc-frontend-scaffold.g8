#!/bin/bash -e

# The script tests hmrc-frontend-scaffold.g8 in CI and local environment

print() {
  echo
  echo -----------------------------------------------------------------------------------------------------------------
  echo $1
  echo -----------------------------------------------------------------------------------------------------------------
  echo
}

# - Generate a service using hmrc-frontend-scaffold.g8 in target/g8

print "INFO: Generating project from hmrc-frontend-scaffold.g8 template"
sbt g8

# - Prepare the project

print "INFO: Copy repository.yaml file into the new project"
cp "repository.yaml" "target/g8/repository.yaml"

# - Compile the new project and run the test suites

print "INFO: Compile and run generated test suites"
cd "target/g8"
sbt "compile; test; it/test"