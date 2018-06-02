# Re-Natal
### A utility for building ClojureScript-based React Native apps
Artur Girenko
[@drapanjanas](https://twitter.com/drapanjanas)

---

Re-Natal is a simple command-line utility that automates most of the process of
setting up a React Native app running on ClojureScript with [Reagent] + [re-frame](https://github.com/Day8/re-frame), [Om.Next] or [Rum].

This project is a fork of [dmotz/natal](https://github.com/dmotz/natal) by Dan Motzenbecker.

Figwheel support is based on the brilliant solution developed by Will Decker [decker405/figwheel-react-native](https://github.com/decker405/figwheel-react-native),
which works on both platforms.


For more ClojureScript React Native resources visit [cljsrn.org](http://cljsrn.org).

Contributions are very welcome.

## Status
- Uses [React Native] v0.55.4
- Reusable codebase between iOS and Android
- Figwheel used for REPL and live coding
  - Works in iOS (real device and simulator)
  - Works in Android (real device and simulators, specifically AVD and Genymotion)
  - Figwheel REPL can be started from within nREPL
  - Simultaneous development of iOS and Android apps
  - Manual reload and automatic hot reload
  - Custom react-native components supported
  - Source maps available
- Supported React wrappers:
[Reagent], [Om.Next], and [Rum]
- Support of windows (UWP and WPF) apps
- [Unified way of using static images of React Native 0.14+](https://facebook.github.io/react-native/docs/images.html) supported

## Compatibility with RN versions
Current version of re-natal might not work correctly with older versions of React Native  
- use current version of re-natal with React Native >= v0.46
- use re-natal < v0.5.0 with React Native < v0.46

## Dependencies
As Re-Natal is an orchestration of many individual tools, there are quite a few dependencies.
If you've previously done React Native or Clojure development, you should hopefully
have most installed already. Platform dependencies are listed under their respective tools.

- [npm](https://www.npmjs.com) `>=1.4`
    - [Node.js](https://nodejs.org) `>=7.1.0`
- [react-native-cli](https://www.npmjs.com/package/react-native-cli) `>=0.1.7` (install with `npm install -g react-native-cli`)
- [watchman](https://facebook.github.io/watchman/) `>=4.9.0`
- [Leiningen](http://leiningen.org) `>=2.5.3`
    - [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
      
For iOS  development:
- [Xcode](https://developer.apple.com/xcode) (+ Command Line Tools) `>=6.3`
    - [OS X](http://www.apple.com/osx) `>=10.10`

## Creating a new project

Before getting started, make sure you have the
[required dependencies](#dependencies) installed.

Then, install the CLI using npm:

```
$ npm install -g re-natal
```

To generate a new app, run `re-natal init` with your app's name as an argument:

```
$ re-natal init FutureApp
```

This will generate a project which uses Reagent v0.6.  
You may specify the -i option to choose a specific React wrapper: Om.Next, Reagent v0.6 or Rum:

```
$ re-natal init FutureApp -i [om-next | reagent6 | rum]
```

If your app's name is more than a single word, be sure to type it in CamelCase.
A corresponding hyphenated Clojure namespace will be created.

The init process will take a few minutes — coffee break! If all goes well you should see basic instructions on how to run in iOS simulator.

## Development with Figwheel

Initially the `index.*.js` files are generated with the production profile, ready for deployment.  
However, during development it is better to use the development profile and integrate with Figwheel. Switch to the development profile with:

```
$ re-natal use-figwheel
```

This command needs to be run every time you switch to the development profile or specify a development environment (with `use-ios-device` or `use-android-device`).

NOTE: You might need to restart React Native Packager and reload your app.

#### Starting a standalone Figwheel REPL

Start a Figwheel REPL from the command line with:

```
$ lein figwheel [ios | android]
```

If all went well you should see the REPL prompt and changes in source files should be hot-loaded by Figwheel.

#### Starting Figwheel REPL from nREPL
You can also start Figwheel from within an existing nREPL session. Start a REPL with `lein repl` or, if using emacs and CIDER for editor integration, `cider-jack-in` (`C-c M-j`). Then in the REPL prompt type:
```
user=> (start-figwheel "ios")
```
Or, for Android build type:
```
user=> (start-figwheel "android")
```
Or, for both type:
```
user=> (start-figwheel "ios" "android")
```

#### Changing the default Figwheel port
In case you have a conflict with the default Figwheel port you can change it as follows:
1. Change the port in project.clj (as per Figwheel docs)
1. Run `re-nata set-figwheel-port <port>`
1. Run `re-natal use-figwheel` 

## Running the app
### Note for Linux users
On Linux, the React Native Packager has to be started manually with
```
react-native start
```
See [here](#running-on-linux) for more details.

### iOS
#### Using iOS simulator

```
re-natal use-ios-device simulator
react-native run-ios
```

#### Using real iOS device

```
re-natal use-ios-device real
```
 
If this doesn't correctly detect your computer's IP you can pass your IP address explicitly: `re-natal use-ios-device <IP address>`.  
And then run

```
react-native run-ios
```

#### Switching between iOS devices
Run `use-ios-device` to configure device type you want to use in development:
```
$ re-natal use-ios-device <real|simulator>
$ re-natal use-figwheel
$ lein figwheel ios
```

---

### Android

#### Using Android Virtual Device (AVD)
[Set up a virtual device in AVD](https://developer.android.com/studio/run/managing-avds.html). Start the virtual device then run
```
$ re-natal use-android-device avd
$ re-natal use-figwheel
$ lein figwheel android
$ react-native run-android
```

#### Using Genymotion simulator
Set up and start the Genymotion simulator then run
```
$ re-natal use-android-device genymotion
$ re-natal use-figwheel
$ lein figwheel android
$ react-native run-android
```

#### Using real Android device
To run figwheel with real Android device please read [Running on Device](https://facebook.github.io/react-native/docs/running-on-device.html).  
To make it work on a USB connected device I also had to run:
```
$ adb reverse tcp:8081 tcp:8081
$ adb reverse tcp:3449 tcp:3449
```
Then:
```
$ re-natal use-android-device real
$ re-natal use-figwheel
$ lein figwheel android
$ react-native run-android
```

#### Switching between Android devices
Run `use-android-device` to configure device type you want to use in development:
```
$ re-natal use-android-device <real|genymotion|avd>
$ re-natal use-figwheel
$ lein figwheel android
```

### Developing iOS and Android apps simultaneously
```
$ re-natal use-figwheel
$ lein figwheel ios android
```

---

### Using external React Native Components

Lets say you have installed an external library from npm like this:
```
$ npm i some-library --save
```

And you want to use a component called 'some-library/Component':
```clojure
(def Component (js/require "some-library/Component"))
```
This works fine when you do `lein prod-build` and run your app. 

The React Native packager statically scans for all calls to `require` and prepares the required
code to be available at runtime. But, dynamically loaded (by Figwheel) code bypasses this scan
and therefore requiring the custom component fails.

In re-natal this is solved by adding all dependencies in index.*.js file which is scanned by React Native packager.

#### Using auto-require

To enable auto-require feature you have to run command:
```
$ re-natal enable-auto-require
```
From now on, command `use-figwheel` will scan for all required modules and generate index.*.js with all required dependencies.
You will have to re-run `use-figwheel` command every time you use new modules via `(js/require "...")` 

This feature is available since re-natal@0.7.0

#### Manually registering dependencies with use-component command

You can register a single dependency manually by running `require` command:
```
$ re-natal require some-library/Component
```
or for a platform-specific component use the optional platform parameter:
```
$ re-natal require some-library/ComponentIOS ios
```
Then, regenerate index.*.js files:
```
$ re-natal use-figwheel
```
Lastly, you will have to restart the packager and reload your app.

NOTE: If you mistyped something, or no longer use the component and would like to remove it,
 manually open `.re-natal` and fix it there (it's just a list of names in JSON format, so the process should be straight forward).

#### Scanning for dependencies with require-all command

If you have used new modules in your code you can run: 
```
$ re-natal require-all
```
This will scan your code for `(js/require ...)` calls and add new required modules automatically.
After this, you still need to run `use-figwheel` command to regenerate index.*.js files. 

## REPL
You have to reload your app, and should see the REPL coming up with the prompt.

At the REPL prompt, try loading your app's namespace:

```clojure
(in-ns 'future-app.ios.core)
```

Changes you make via the REPL or by changing your `.cljs` files should appear live
in the simulator.

Try this command as an example:

```clojure
(dispatch [:set-greeting "Hello Native World!"])
```

## Running on Linux
In addition to the instructions above on Linux you might need to
start React Native packager manually with command `react-native start`.
This was reported in [#3](https://github.com/drapanjanas/re-natal/issues/3)

See this [tutorial](https://gadfly361.github.io/gadfly-blog/posts-output/2016-11-13-clean-install-of-ubuntu-to-re-natal/) on how to set up and run re-natal on a clean install of Ubuntu.

## Support of UWP and WPF apps (using react-native-windows)

To start new project with UWP app:
```
$ re-natal init FutureApp -u
```  

To start new project with WPF app:
```
$ re-natal init FutureApp -w
```  

Existing projects can also add windows platforms any time using commands:
```
$ re-natal add-platform windows

or

$ re-natal add-platform wpf
```  
Note: for projects generated with re-natal version prior to 0.4.0 additional windows builds will not be added automatically to `project.clj`. 
Workaround is to generate fresh windows project and copy-paste additional builds manually.  

## Production build
Do this with command:
```
$ lein prod-build
```
Follow the [React Native documentation](https://facebook.github.io/react-native/docs/signed-apk-android.html) to proceed with the release.

#### Advanced CLJS compilation
```
$ lein advanced-build
```
The ReactNative externs are provided by [react-native-externs](https://github.com/mfikes/react-native-externs)
Other library externs needs to be added manually to advanced profile in project.clj  

## Static Images
Since version 0.14 React Native supports a [unified way of referencing static images](https://facebook.github.io/react-native/docs/images.html)

In Re-Natal skeleton images are stored in "images" directory. Place your images there and reference them from cljs code:
```clojure
(def my-img (js/require "./images/my-img.png"))
```
#### Adding an image during development
When you have dropped a new image to "images" dir, you need to restart React Native packager and re-run command:
```
$ re-natal use-figwheel
```
This is needed to regenerate index.\*.js files which includes `require` calls to all local images.
After this you can use a new image in your cljs code.

If your images aren't able to be required after running `re-natal use-figwheel`,
you may need to also run `react-native run-android`.

## Upgrading existing Re-Natal project

#### Upgrading React Native version

To upgrade React Native to newer version please follow the official
[Upgrading](https://facebook.github.io/react-native/docs/upgrading.html) guide of React Native.
Re-Natal makes almost no changes to the files generated by react-native so the official guide should be valid.

#### Upgrading Re-Natal CLI version
Do this if you want to use newer version of re-natal.

Commit or backup your current project, so that you can restore it in case of any problem ;)

Upgrade re-natal npm package
```
$ npm upgrade -g re-natal
```
In root directory of your project run
```
$ re-natal upgrade
```
This will overwrite only some files which usually contain fixes in newer versions of re-natal,
and are unlikely to be changed by the user. No checks are done, these files are just overwritten:
  - files in /env directory
  - figwheel-bridge.js

Then to continue development using figwheel
```
$ re-natal use-figwheel
```

## Enabling source maps when debugging in chrome
To make source maps available in "Debug in Chrome" mode re-natal patches
the react native packager to serve \*.map files from file system and generate only index.\*.map file.
To achieve this [this line](https://github.com/facebook/react-native/blob/0.29-stable/packager/react-packager/src/Server/index.js#L413)
of file "node_modules/react-native/packager/react-packager/src/Server/index.js" is modified to match only index.\*.map

To do this run: `re-natal enable-source-maps` and restart packager.

You can undo this any time by deleting `node_modules` and running `re-natal deps`

## Example Apps
* [Luno](https://github.com/alwx/luno-react-native) is a demo mobile application written in ClojureScript.
* [Re-Navigate](https://github.com/vikeri/re-navigate) example of using new Navigation component [NavigationExperimental](https://github.com/ericvicenti/navigation-rfc)
* [Showcase of iOS navigation](https://github.com/seantempesta/om-next-react-native-router-flux) with react-native-router-flux and Om.Next
* [Catlantis](https://github.com/madvas/catlantis) is a funny demo application about cats
* [Lymchat](https://github.com/tiensonqin/lymchat) App to learn different cultures. Lym is available in [App Store](https://itunes.apple.com/us/app/lym/id1134985541?ls=1&mt=8).
* [Status](https://status.im) ([Github](https://github.com/status-im/status-react/)): a web3 browser, messenger, and gateway to a decentralised world of Ethereum. re-natal + re-frame + cljs + golang  
* [React Native TodoMVC](https://github.com/TashaGospel/todo-mvc-re-natal) is a mobile application which attempts to implement TodoMVC in Clojurescript and React Native.

## Tips
- Having `rlwrap` installed is optional but highly recommended since it makes
the REPL a much nicer experience with arrow keys.

- Running multiple React Native apps at once can cause problems with the React
Packager so try to avoid doing so.

- You can launch your app on the simulator without opening Xcode by running
`react-native run-ios` in your app's root directory (since RN 0.19.0).

- To change advanced settings run `re-natal xcode` to quickly open the Xcode project.

- If you have customized project layout and `re-natal upgrade` does not fit you well,
then these commands might be useful for you:
    * `re-natal copy-figwheel-bridge` - just copies figwheel-bridge.js from current re-natal

## Local Development of Re-Natal

If you would like to run any of this on your local environment first clone the code to an appropriate place on your machine and install dependencies

```
$ git clone https://github.com/drapanjanas/re-natal.git
$ cd re-natal
$ npm install
```

To test any changes made to re-natal, cd to an already existing project or a brand new dummy project:

```
$ cd ../already-existing
```

and run the re-natal command line like so

```
$ node ../re-natal/index.js

  Usage: re-natal [options] [command]


  Commands:

    init [options] <name>              create a new ClojureScript React Native project
    upgrade                            upgrades project files to current installed version of re-natal (the upgrade of re-natal itself is done via npm)
    add-platform <platform>            adds additional app platform: 'windows' - UWP app, 'wpf' - WPF app
    xcode                              open Xcode project
    deps                               install all dependencies for the project
    use-figwheel                       generate index.*.js for development with figwheel
    use-android-device <type>          sets up the host for android device type: 'real' - localhost, 'avd' - 10.0.2.2, 'genymotion' - 10.0.3.2, IP
    use-ios-device <type>              sets up the host for ios device type: 'simulator' - localhost, 'real' - auto detect IP on eth0, IP
    use-component <name> [<platform>]  configures a custom component to work with figwheel. name is the value you pass to (js/require) function.
    enable-source-maps                 patches RN packager to server *.map files from filesystem, so that chrome can download them.
    copy-figwheel-bridge               copy figwheel-bridge.js into project

  Options:

    -h, --help     output usage information
    -V, --version  output the version number
```

You can then run any of the commands manually.

[React Native]: https://facebook.github.io/react-native
[Reagent]: https://github.com/reagent-project/reagent
[Om.Next]: https://github.com/omcljs/om/wiki/Quick-Start-(om.next)
[Rum]: https://github.com/tonsky/rum
