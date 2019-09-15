# 2 Apps communicating with each other via Intent and one localization app

Section 1 - 2 Apps communicating with each other
- [frontendapp](apicaller/client/frontendapp) - user interfacing app, receive input from user
- [apiconsumerapp](apicaller/client/apiconsumerapp) - background app, receive Intent from frontendapp and communicate with apiserver, then forward result back to frontendapp
- [apiserver](apicaller/server) - [HomeController](apicaller/server/app/controllers/HomeController.scala), just an echo server keeping track of call count and do simple calculation and return result to client

Section 2 - Localization app using Android's String resources
- [localizationapp](apicaller/client/localizationapp) - the client Android app
- [apiserver](apicaller/server) - [LocalizationController](apicaller/server/app/controllers/LocalizationController.scala), returns the localization JSON. This could be just .json file hosted somewhere but I just add an API endpoint to the apiserver

All the apps built are targeted to fulfil extended requirements at the very minimum, they may 'just work' without regards to user experience (ease of setup or ease of configuration)

## Getting Started

For the client apps, open Android Studio and navigate to /apicaller/client
All the 3 apps [frontendapp, apiconsumerapp, localizationapp] should be there
* Select apiconsumerapp and run - this app needs to be installed first, otherwise frontendapp will popup a complain
* Select frontendapp and run - assuming that server is run on localhost, and the app on emulator on the same machine, all the functionality should be working
  Go to apiconsumerapp/Constant.java to modify the API Server's url if you run the server on different machine
* Select localizationapp

To build and run the server, you'll need SBT (Scala Build Tool), if it is not installed in your machine, run:

```
brew install sbt@1
```

Alternatively, a [docker-image](apicaller/server/docker-image.zip) has been provided, under server/docker-image.zip

### apiconsumerapp and frontendapp

There are a couple of other ways to solve third party app needing to communicate with our app, the apiconsumerapp might be developed as a Service within a host app that does other thing (eg. another store-front app), or it can also exist just as a BroadcastReceiver within the host. The communication can then be using BroadcastIntent and the apiconsumerapp can complete everything in the background without the user experiencing "switching app" (The switch activity feeling when the user jump to apiconsumerapp's MainActivity from frontendapp's MainActivity and then back to frontendapp's). The drawback of using that approach would be the host app needs to be started at least once after being installed (so it has to have Activity), otherwise it might not receive the Broadcasts.

The Activity approach (apiconsumerapp handles everything in Activity) is used because it is the simplest and fastest way to fulfil the flow in exact manner. I do not know the entire design but in reality, apiconsumer app might even need to accomplish some flow that requires Activity, e.g: payment checkout flow

### localizationapp

There are also a couple other ways to do dynamic localization, ie: switching some part of the app's text to other language for either static or dynamic texts

Using Android's built-in Resources Strings are the fastest and easiest as we don't need to write custom solutions and it is also 'automatically done' for all our UIs if we follow Android's guidelines (we don't need to worry about user switching locale etc)
However, Android's approach is mainly catered for user from different locale, or switching locale, thus the 'entire' app's texts will be switched automatically. If we need to translate some texts but not the other, or use some texts from locale different from user's default, we will need to do the stuff like demonstrated in the localizationapp
Usually, we would use the Android's built-in Resources Strings to handle the automatic locale switching on the UI, but for translating texts to other languages, we will develop custom solutions according to needs