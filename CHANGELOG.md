# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/).

## v0.31.0 - 2023-11-21

### Changed
* Updated LocalDate formatter to allow months to be supplied as names (e.g. January) or abbreviations (e.g. Jan)
* Updated dependencies

### Fixed
* Make sure errors messages when parts of a date are omitted are internationalised

## v0.30.0 - 2023-07-05

### Changed
* Removed unnecessary page tests and associated generators

## v0.29.0 - 2023-07-04

### Changed
* Updated to scala 2.13
* Updated to sbt 1.7.2
* Updated library dependencies

## v0.28.0 - 2022-09-16

### Fixed
* Fix back link bug in layout

## v0.27.0 - 2022-08-17

### Fixed
* `.asNumeric()` on `InputFluency` now correctly sets the inputmode
## v0.26.0 - 2022-08-10

### Changed
* Remove deprecated FrontendFilters

## v0.25.0 - 2022-08-08

### Changed
* Update configuraiton of session id filter
* Updated library dependencies

## v0.24.0 - 2022-06-17

### Changed
* Updated library dependencies

## v0.22.0 - 2022-01-05

### Changed
* Updated library dependencies

### Fixed
* CheckboxesViewModel now selects items correctly regardless of display order of items

## v0.21.0 - 2021-03-21

### Changed
* Upgraded to Play 2.8
## v0.20.0 - 2021-03-09

### Changed
* Replaced assets-frontend and play-ui with play-frontend-hmrc and play-frontend-govuk
* Added "viewmodel helpers" to make working with play-frontend components simpler
* Replaced CheckYourAnswersHelper with viewmodels that provide `SummaryListRow`s
* Added timeout dialog to pages by default
* Included tracking consent snippet by default
* Replaced reactive-mongo with hmrc-mongo
* Included "sign out" links on appropriate pages
* Replaced SessionExpired controller with JourneyRecovery controller to better reflect its usage
* Updated sbt version to 1.13.13
* Updated libraries and dependencies

## v0.19.0 - 2021-02-22

### Changed
* Updated dependencies and test libraries
* Consolidated unit tests to AnyFreeSpec and Matchers
* Updated SessionRepository to keep session alive on gets

### Fixed
* Allow args to be passed to formatters / mappings
* Fix genIntersperseString generator to preserve order of input

## v0.18.0 - 2020-11-17

### Fixed
* Update akka dependencies

## v0.17.0 - 2020-10-26

### Changed
* Upgrade to Play 2.7
* Remove SessionIdFilter and WhitelistFilter and use the versions supplied by `bootstrap-play`

## v0.16.0 - 2020-10-07

### Changed
* Use play-language library for switching language
* Update dependencies
* Update to scala 2.12

### Fixes
* Ensure date forms are re-initialised each time a controller method is invoked
* Change UserAnswers to use Gettable and Settable rather than QuestionPage, to allow for queries
* Change UserAnswers.remove to use removeObject rather than setting to JsNull

## v0.15.0 - 2020-02-28

### Changed
* Updated libraries and plugins, including upgrading to SBT 1.3.6

## v 0.14.0 - 2019-10-08

### Changed
* Updated to use Assets Frontend 4.11

### Fixed
*  Updated palette and markup for better WCAG 2.1 compliance

## v0.13.0 - 2019-09-25

### Changed
* Updated libraries and plugins
* Change repository to use newer update method

## v0.12.0 - 2019-09-24

### Changed
* Upgraded to Play 2.6
* Refactored user answers to use a Json object rather than CacheMap
* Introduce paths on each page to allow pages to decide where in the Json object to store data
* Introduce queries, which can be used to look at data in UserAnswers
* Add a scaffold for a date page
* Add a scaffold for a page of checkboxes
* Updated navigator pattern
* Html escape users' answers on CYA pages
* Updated libraries and plugins
* Update assets frontend version

## Fixed
* Stop application in unit tests
* Mock repositories in controller unit tests
* Remove unsafe-inline and data: from CSP
* Fix textarea whitespace issue
* Fix auth action recover block
* Allow field names to be specified on quesitonPage

## v0.11.0 - 2018-09-25

### Changed
* Change plugins to work on New Build
* Migrate to build.sbt
* Moved utility code to better homes
* Improved the textarea component

## v0.10.0 - 2018-09-10

### Changed
* Change where data cleanup is managed
* Allow port to be specified when creating a new project
* Removed some redundant code

### Fixed
* Add back link to pages which were missing it

## v0.9.0 - 2018-07-17

### Changed
* Add "Error: " prefix to page titles on error condition
* Update Check your answers page to latest styles
* Prevent resubmission warnings when going back following submission errors

### Fixed
* Bring error and error summary styling in line with GOV.UK

## v0.8.0 - 2018-07-10

### Changed
* Turn off HMRC logo by default
* Add whitelist filter

### Fixed
* Bug in migration scripts that led to (harmless) errors when multiple scaffolds were migrated in one go
* Add config to prevent leak detection being triggered incorrectly

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
