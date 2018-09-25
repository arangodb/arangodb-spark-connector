# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

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

[unreleased]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.7...HEAD
[1.0.7]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.6...1.0.7
[1.0.6]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.5...1.0.6
[1.0.5]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.4...1.0.5
[1.0.4]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.3...1.0.4
[1.0.3]: https://github.com/arangodb/arangodb-spark-connector/compare/1.0.2...1.0.3
