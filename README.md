RxMVP Example
=============

Showcases a reactive MVP approach via a 'Show Weather Forecast' example.

Contains examples both in [RxJava 1.x][rxjava1] and [RxJava 2.x][rxjava2]:
 - [rxjava-1.x][rxjava-1.x] branch contains an example using RxJava 1.x
 - [rxjava-2.x][rxjava-2.x] branch contains an example using RxJava 2.x

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

##

Created for a tech talk **MVP & RxJava 2.0** in collaboration with [@vanniktech](https://github.com/vanniktech).

[rxjava1]: https://github.com/ReactiveX/RxJava/tree/1.x
[rxjava2]: https://github.com/ReactiveX/RxJava/tree/2.x
[rxjava-1.x]: https://github.com/serj-lotutovici/RxMVP/tree/rxjava-1.x
[rxjava-2.x]: https://github.com/serj-lotutovici/RxMVP/tree/rxjava-2.x 
