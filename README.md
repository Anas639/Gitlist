# Gitlist
An Android application that lists the most starred Github repos that were created in the last 30 days

## Running the project / Getting started
This project was made using Android Studio 3.3, in order to run this on your Android phone/Emulator you need to download the 
whole project and open it using Android Studio.

you can clone it using

```shell
git clone https://github.com/Anas639/Gitlist.git
```

## Features

### Application features :

* A nice user interface that lists the most starred github repos
* Dynamically appending new repos when scroll reaches bottom

### Code source Features :
*Clean
*Resusable

## Code source explanation

### Behavior
This is an abstract class that represents a Behavior able to get executed
The Execute method can be overriden
all Behaviors can be passed as parameters to functions and act like a callback
### DataReceivedBehavior
This is a Behavior that contains an extra field "_data" it can hold any type of data since in java everything is an Object
We can use this as a callback that contains some sort of data
in this project it is used when we read JSON from github API _data contains either an error or JSONObject
### DateUtil
This class has a potential to contain various DateUtils, but at this moment it only susbtract days from _date field
and print the result in the "yyyy-MM-dd" format.
This class uses the chain method so it can get exploited in one line
### ErrorCodes
We can call this a registry for error codes.
It holds error codes so object can share error codes easily without the chance of a mistake
### GitApiURLFactory
This is the factory that generates the URL to the API, it takes care of what you want from the API and parameters also
The method URL() returns the full url as a String
### GitPager
This class is nothing special but a page counter
### JSONReader
We can assume that this is the core of the application, a class that reds JSON from a given url
using fromURL(String url,DataReceivedBehavior drb).
the Behavior here acts like a callback, on success it will contain the JSONObject fetched from the url, otherwise
it will contain an Integer (a field value from ErroCodes class) representing the error that occurred during the operation
### RepoListItem
This is a custom view that represents the Repo item that will show on the list
The LoadData method accepts a JSONObject that contains all repo's informations
It changes the labels of custom view depending on JSONObject and downloads the avatar, as well as calculating the suffix for number of stars
### Scroll
This is a custom scroll view (extends ScrollView) that has the ability to detect scroll change and Execute a behavior for that reason.
Since ScrollView does not allow access to scroll change, I had to wrap it with this class.
### ScrollBehavior
This is a Behavior that has extra fields keeping track of the new scroll and old scroll

