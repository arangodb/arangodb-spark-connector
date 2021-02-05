# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

- set `jackson-dataformat-velocypack` as default serializer
- replaced velocypack modules with the jackson modules counterparts

## [1.1.0] - 2020-04-17

- insert, update and replace write option
- update java driver version to 6.6.2

## [1.0.13] - 2020-01-15

- updated java driver version to 6.5.0
- fixed null Seq entries
- fixed null values handling

## [1.0.12] - 2019-09-12

### Changed
- update java driver version to 6.2.0

### Fixed
- Array Support

## [1.0.11] - 2019-07-19

### Added
 - support for acquireHostListInterval

### Fixed
- update version

## [1.0.10] - 2019-06-14

### Changed
- update java driver version to 5.0.6

## [1.0.9] - 2019-05-24

### Changed
- update java driver version to 5.0.5


## [1.0.8] - 2018-11-23

### Added
- added load balancing

### Changed
- upgraded dependency spark-core and spark-sql to 2.4.0

## [1.0.7] - 2018-09-25

### Fixed

- upgraded dependency arangodb-java-driver 5.0.1
  - fixed connection stickiness

## [1.0.6] - 2018-09-03

### Fixed

- fixed partitioning with `ArangoSpark#load`

## [1.0.5] - 2018-09-03

### Fixed

- fixed partitioning with `ArangoSpark#load`

## [1.0.4] - 2018-08-28

### Added

- added config `arangodb.protocol`

## [1.0.3] - 2018-07-18

### Changed

- upgraded dependency arangodb-java-driver 4.6.1

## [1.0.2] - 2017-11-30

### Added

- added SSL support
- added velocypack-module-jdk8
- added velocypack-module-scala

[unreleased]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.11...HEAD
[1.0.11]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.10...1.0.11
[1.0.10]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.9...1.0.10
[1.0.9]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.8...1.0.9
[1.0.8]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.7...1.0.8
[1.0.7]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.6...1.0.7
[1.0.6]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.5...1.0.6
[1.0.5]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.4...1.0.5
[1.0.4]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.3...1.0.4
[1.0.3]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.2...1.0.3
