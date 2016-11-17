# campaign-optimizer
a restful service that expects a csv input and return a csv output.

## Building the service
use java 7 and maven 3.x

inside the campaign-optimizer/ run 

	maven clean install

## Running the service

	java -Xms512M -Xmx1024M -jar target/campaign-optimizer-0.0.1-SNAPSHOT.jar server

## Testing the service
	
	curl -X POST http://localhost:8080/campaignOptimizer/ --data-binary "@../samples/sample_2.csv" -H "Content-Type: text/csv" -H "Accept: text/csv"

