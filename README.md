![Build Status](https://github.com/endrealm/RealmDrive/workflows/Java%20CI/badge.svg)![GitHub contributors](https://img.shields.io/github/contributors/endrealm/realmdrive.svg)[![Maven Central](https://img.shields.io/maven-central/v/net.endrealm/realm-drive.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22net.endrealm%22%20AND%20a:%22realm-drive%22)
# RealmDrive
Adapter to allow using and switching different backends in Java without a problem. Replaces the general need of using Query languages. More information on why to use RealmDrive can be found [here](/docs/why.md#why-to-use-realmdrive).

**Features:**
 - Easy setup and usage
 - Easy query building that translates into all supported datbases.
 - Automatic java object <-> database entity conversion
 - Support to define serializers for existing classes like java.util.Date

Currently supported database systems:
 - MongoDB
 
 Soon to be supported:
 - MySQL
 
## Contents
1. [Why use RealmDrive?](./docs/why.md#why-to-use-realmdrive)
2. [How to contribute](./CONTRIBUTING.md#contributing)
3. [Getting started & usage](./docs/usage.md)
4. [Supported Drivers](./docs/driver-support.md)
