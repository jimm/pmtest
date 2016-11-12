# pmtest

A Clojure library that uses the Java PortMidi library.

See also https://github.com/Chouser/clojure-jna which would let me bypass
the JPortMidiApi class completely.

## Usage

FIXME

To install the local jar file:
```sh
mvn install:install-file -Dfile=lib/jportmidi.jar \
    -DgroupId=portmidi -DartifactId=jportmidi -Dversion=1.0.0 -Dpackaging=jar
```

## License

Copyright © 2016 Jim Menard

Distributed under the MIT license.
