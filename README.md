RxMVP Example
=============

TODO

## Project Structure

The projects consists of tree source containers:
* `src/main/` - contains project code
* `src/test/` - contains unit tests for business logic
* `src/androidTest` - contains functional ui tests

The main code can be divided into the following "layers":
* `data` - configuration of the network component + java representation of the response.
* `di` - configuration of the applications global injector (dependency graph).
* `rx` - declaration of the RxModule, basically a scheduler provider.
* `show.forecast` - user facing feature, designed following the MVP pattern.
