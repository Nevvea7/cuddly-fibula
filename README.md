# cuddly-fibula
cuddly-fibula is a demo Android app with stuff like Material Design, Yelp API, AsyncTask, etc. User can search for restaurants, get directions, call restaurants, go see it on Yelp from the app. 

## Compile and Run
You can either clone this repo and run it with the source code, or you can [get the apk](cuddly-fibula.apk)

## Index
- [Structure](#Structure)
	- Activities
	- Why not fragments
	- On location
	- AsyncTask
- [Material Design](#Material-Design)
- [Activity vs Fragments](#Activity-vs-Fragments)
- [Stability and State](#Stability-and-State)

## Structure
	### Activities
	The app has two activities--MainActivity and DetailActivity. MainActivity is for searching and seeing the list of restaurants. DetailActivity is for displaying detailed information about a single restaurant.
	### Why not fragments
	I originally thought I'd have one Activity with two fragments. But since I'm not making super fancy layouts with dynamically positioned fragments I went with two Activities. If I were to make another layout for tablets with the Master-Detail layouts on the same page fragments would be handy.
	### On location
	If I had more time I'd let the app detect where the user is and make the search centered around that area instead of forcing the user to enter a location, but in this case I don't want to get involved in Google API just for that.
	### AsyncTask
	So when the user hits "Go!" the app runs an AsyncTask to get search results from Yelp. I had thought about separating the bulk searching and the detail searching to two AsyncTasks that calls Yelp's Search and Business API, but the Business API doesn't provide much more detail than the Search API, so I decided to do just one and pass the result object between Activities. I'd also like to show more information in the app but that's pretty much all I can get from the API.

## Material Design
This is my 1.5th time using material design and the support package, and it's quite delightful. I chose the same red as the primary color and the accent color because it stands out, and I don't want a less passionate color in there.
Icons in this app are from Google's [Material Icons Library](https://www.google.com/design/icons/)

## Stability and State
The app does not crash in cases such as no internet connection, empty or invalid search terms and when calling other apps (it hides the "call" option if the restaurant's phone number is not available). It also saves the app's state (e.g. activities don't restart and go blank) when you rotate the phone or hit the up button.