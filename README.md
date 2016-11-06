## Synopsis
Buedchen-Server provides the RESTful Services which allows the user to create their own personalized channels (bundle of URLs to be displayed on the display/monitoring systems). Then the user can set the time period for each URL (i.e how long content of each URL has to be displayed). Once the channel is created and the application is running, the server pushes the next URL to connected clients through the web socket after time period for each URL is elapsed.<br />
The server also provides REST endpoints to configure the providers/projects which provides centralized information for all the users. Using the UI provided in the buedchen-client (chrome application) the user can easily add the URL from providers to their channels.<br />
*Also read [buedchen-client](https://github.com/buedchen/buedchen-client) to get a better understanding of the application.*

## RESTful Services
The below image shows the screenshot of Swagger documentation of REST api for configuring the channel. It lists out the REST endpoints along with additional information like functionality, required parameters, response type etc. Swagger documentation allows the user to interact/query the REST API.<br />
Once the server is hosted the swagger documentation is available in the below path,<br />
*server_url/docs/*<br />
*Example:* http://localhost:8080/docs/<br />

![Buedchen API](images/ChannelsApi.png?raw=true)

## Getting Started

### Prerequisites
Tools to be installed
* git
* maven
* H2 database
* IDE for java

### Setup
Initializing the Server:
    
     Server server = new BuedchenServer.Builder()
                    .restContext()	 // builds the servlet context for the REST API
                    .swaggerUIContext()  // servlet context for Swagger documentation for REST API (optional).
                    .wsContext()		 // Sets the servlet context for the Web Socket.
                    .build(Integer.valueOf(System.getenv("PORT")));	// builds the server and starts server on the defined environment variable PORT.        

## Features
* **Project configuration:**
Project configuration providers the centralized information of URLs to all the clients. Using the URLs provided in the providers (projects) the user can configure the channels.
* **Channel Configuration:**
The feature allows the user to create a personalized channel for their monitoring system. Also, the user can define the time period for each URL with their channel. 
* **Swagger Documentation:**
Swagger documentation is the interactive documentation of the RESTful APIs.
Find more info about swagger [here](http://swagger.io/).
* **Status of the URLs:**
Within the project configuration, the statuses(active/unavailable) of all the URLs is updated every 60 seconds.

## API Reference
The buedchen-server is deployed on heroku for the user reference.<br />
Swagger documentation for the buedchen REST API can be found [here](https://buedchen.herokuapp.com/docs).

## Example Usage
Both the channels and providers can be configured using swagger documentation or command line CURL calls. Below are the steps to configure channel and providers using CURL calls.
* Setting up a channel:
   1. Creating a channel:<br />
      **CURL command to add a channel:**<br />
    curl -X POST --header 'Content-Type: application/json' --header<br />
'Accept: text/html' -d '{ <br />
   "channel_id": "string", <br />
   "channel_description": "string"  
 }' 'server_url/api/v1/channels'<br />
**Example:**<br />
curl -X POST --header 'Content-Type: application/json' --header<br />
'Accept: text/html' -d '{ <br />
   "channel_id": "pavan", <br />
   "channel_description": "channel for pavan's monitoring system" <br />
 }' 'http://localhost:8080/api/v1/channels'

   2. Adding URL to a channel: <br />
CURL command to add a channel:<br />
curl -X POST --header 'Content-Type: application/json' --header<br />
'Accept: application/json' -d '{<br />
   "url": "string", <br />
   "showtime": 0,<br />
   "title": "string",<br />
   "description": "string"<br />
 }' 'server_url/api/v1/channels/channel_id/contents'<br />
**Example:**<br />
curl -X POST --header 'Content-Type: application/json' --header<br />
'Accept: application/json' -d '{<br />
   "url": "https://status.github.com/",<br />
   "showtime": 60,<br />
   "title": "Github Status",<br />
   "description": "Dashboard for Github Status"<br />
 }' 'http://localhost:5000/api/v1/channels/pavan/contents'

* Setting up providers<br />
The providers can be setup in a similar way to channels. Refer to [swagger documentation](http://buedchen.herokuapp.com/docs/) for information on configuring the providers.

## Contributing
1. Fork it!
2. Create your feature branch: git checkout -b feature/my-new-feature
3. Commit your changes: git commit -am 'Added my-new-feature'
4. Push to the branch: git push origin my-new-feature
5. Submit a pull request

## License
Eclipse Public License - v 1.0

## Authors
* Tobias Ullrich
* Frank Wisniewski
* Pavan Manakatti





