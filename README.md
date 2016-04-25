# todos

Implementation of http://todomvc.com/ in scala, using twitter's [finatra](https://twitter.github.io/finatra/).

### Tests
#### External dependencies
Dedicated [`redis`](http://redis.io/) instance is required for running tests. Tests modify the `redis` data-store, by deleting all key-values.

Tests in this project expect `redis` server to listen on `localhost:7295`.

#### Run
```bash
$ sbt test
```
