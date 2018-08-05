# Coffiend
An Android application which utilizes Kotlin, Retrofit, Gson, Google Maps and Places API to show the nearest coffee shops

The name is a portmanteau of the two words "Coffee" and "Fiend"

1.  Clone repo
2.  Open in Android Studio
3.  It will use the latest Kotlin version, and some other libs like Retrofit, Mockito
4.  You should be able to press the run button at the top and either run the application on a connected Android device or run it on an emulator that you have setup within Android Studio.

I ran through Google's query limit pretty fast on the API and got locked out from doing more testing for a day or two as a result.
Hopefully I got enough testing in beforehand :)

If you run the app and only see your location (and not surrounding coffee shops), it's most likely because my (free version) API key is over it's query limit.

In that case, the API will return something like this:

{
"error_message": "You have exceeded your daily request quota for this API.",
"html_attributions": [],
"results": [],
"status": "OVER_QUERY_LIMIT"
}

If I had more time to dedicate, I could handle this scenario and show a toast to the user that, sorry, I can't get any data right now because I haven't paid Google for more service.  :)

I structured the project in a MVC fashion, with modules for View, Controllers, and Models.  There is also a networking module which makes use of third party lib Retrofit and Gson.
