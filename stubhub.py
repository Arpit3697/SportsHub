import requests;
import json;
import datetime
import pymysql

db = pymysql.connect("localhost","root","root","sportshub" )
cursor = db.cursor()

headers = {'Accept': 'application/json', 'Authorization': 'Bearer WQk0KeuE1BdFiwTxX99o7nQOwfZC'}
response = requests.get("https://api.stubhub.com/sellers/search/events/v3?date=2019-11-15%20TO%202019-11-16&country=US&categoryName=Sports&rows=500&sort=eventDateLocal%20desc", headers=headers)
data = response.json()
numberofResult = data['numFound']
print(numberofResult)
events = data['events']
count = 0
for i in range(len(events)):
	try :
		eventid = events[i]['id']
		print(eventid)
		sportscategory = events[i]['ancestors']['categories'][1]['name']
		if(sportscategory == "Hockey" or sportscategory == "Basketball" or sportscategory == "Football" or sportscategory == "Soccer" or sportscategory == "Baseball" or sportscategory == "Volleyball"):
			eventname = events[i]['name']
			eventDateLocal = events[i]['eventDateLocal']
			place = events[i]['venue']['name']
			city = events[i]['venue']['city']
			state = events[i]['venue']['state']
			zipcode = events[i]['venue']['postalCode'] 
			
			count +=1
			sql = "Insert into eventcatalog (eventid, eventname, sportscategory, eventdate, eventplace, eventcity, eventstate, eventzipcode, discount) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s)"
			val = (eventid,eventname,sportscategory,eventDateLocal,place,city,state,zipcode,0.0)
			cursor.execute(sql,val)
		else:
			print(sportscategory)
	except:
  		print("An exception occurred")
print(count)
db.commit()

