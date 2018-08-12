# Coffiend
An Android application which utilizes Kotlin, Retrofit, Gson, Google Maps and Places API to show the nearest coffee shops

The name is a portmanteau of the two words "Coffee" and "Fiend"

If you want to build it locally...

1.  Clone repo
2.  Open in Android Studio
3.  It will use the latest Kotlin version, and some other libs like Retrofit, Mockito
4.  You should be able to press the run button at the top and either run the application on a connected Android device or run it on an emulator that you have setup within Android Studio.

If you're cloning this project and attempting to build, you will have to get your own API key for the Google Places API and Google maps and insert the key string into the placeholder for the API key in google_maps_api.xml file.

I structured the project in a MVC fashion, with modules for View, Controllers, and Models.  There is also a networking module which makes use of third party lib Retrofit and JSON-deserialization library Gson from Google.
