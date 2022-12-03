# meshanka

generated using Luminus version "4.38"

If starting anew. Create `user` in `psql` terminal with `CREATE USER <user-name> WITH PASSWORD '<password>'`;
Check that `user-name` and `password` are the same as in `meshanka/dev-config.edn`

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    `lein run` 

Then start CLJS compiler:

    `npx shadow-cljs watch app`

## License

Copyright © 2022 Sen Mikhalev

Build an uberjar locally: 
- `lein uberjar`
- `java -Ddatabase-url="jdbc:postgresql://localhost/meshanka?user=megatron" -jar ./target/uberjar/meshanka.jar`
# meshanka
