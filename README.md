# SimpleWeatherApp

Android weather app written in Kotlin.
Used third party libs: 
- picasso, 
- retrofit2 with rxjava2

Used weather api: https://www.aerisweather.com/support/docs/api/

- client_id and client_secret is needed to run app, get it here: https://www.aerisweather.com/account/apps and place your id and secret -- inside strings.xml
Places SDK for Android is also needed, get it here https://developers.google.com/places/android-sdk/signup and place your key inside manifest meta-data.

TODO:
- User preferences ( *C, *F) etc
- User can't request weather in same location for x time.
- Replcace SQLITE with Anko Sqlite 
- Place text inside strings.xml
- Work on better layout
