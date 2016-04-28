# todos

Implementation of http://todomvc.com/ in scala, using twitter's [finatra](https://twitter.github.io/finatra/).

### Tests
#### External dependencies
[`MySQL`](https://www.mysql.com/) server is required for running tests.

Tests expect to find `todos` database with username `todoer` and password `todoer`.

#### Run
```bash
$ sbt test
```
