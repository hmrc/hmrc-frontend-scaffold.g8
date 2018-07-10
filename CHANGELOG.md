# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/).

## v0.7.0 - 2018-07-10

### Changed
* Added support for session-based storage (for non-auth services)
* Updated bootstrap-play-25 to v1.7.0
* Updated play-ui to v7.17.0
* Updated play-reactivemongo to v6.2.0
* Updated http-caching-client to v7.1.0
* Updated sbt-aut-build to v1.8.0

## v0.6.0 - 2018-03-01

### Changed
* Amended the optionsPage to include additional questions for the page title and the two radio buttons
* Added extra loggers to logback.xml to reduce library logging down to INFO level and reduce the default chatter
* Updated bootstrap-play-25 to v1.4.0
* Updated assets-frontend to v3.2.2
* Changed views to use heading component
* Changed default h1 size to large

### Fixed
* Bug in int page controller tests affecting pages with low maximum values

## v0.5.0 - 2018-01-18

### Added
* New behaviours for forms, using propery based tests
* Generators to facilitate property based testing
* An `inRange` constraint on int page scaffold
* A `maxLength` constraint on string and question page scaffolds

### Changed
* All scaffolds to use property testing for forms
* Updated bootstrap-play-25 to v1.3.0

## v0.4.1 - 2017-12-21

### Fixed
* Hardcoded name in options model
* Unnecessary imports

## v0.4.0 - 2017-12-21

### Changed
* Radio and yes/no components to use new markup
* Assets frontend version to 3.0.1
* Default heading class to heading-xlarge

## v0.3.0 - 2017-12-06

### Added
* Field mappings for text, int, boolean and enumerable
* Formatters for strings, ints, booleans and enumerables
* Constraints for max and min value, max length, regex
* Constraint to present only the first of multiple errors

### Changed
* All scaffolds to use new field mappings and constraints

## v0.2.1 - 2017-12-01

### Fixed
* An issue with the stringPage scaffold (c421d3ec12bad470590d896ed00a91906b054c86)

## v0.2.0 - 2017-11-30

### Added
* This changelog
* stringPage scaffold ([#3](https://github.com/hmrc/hmrc-frontend-scaffold.g8/pull/3))
