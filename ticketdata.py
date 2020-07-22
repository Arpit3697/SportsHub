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
	try:

		eventid = events[i]['id']
		url='https://api.stubhub.com/sellers/find/listings/v3/?eventId=' + str(eventid)
		#url='https://api.stubhub.com/sellers/find/listings/v3/?eventId=104015678'
		response1 = requests.get(url, headers=headers)
		ticketdata= response1.json()
		#print(ticketdata)
		#print(eventid)
		listings=ticketdata['totalListings']
		#print(listings)
		for j in range(10):
			listingid= ticketdata['listings'][j]['listingId']
			#print(listingid)
			quantity= ticketdata['listings'][j]['quantity']
			#print(quantity)
			sectionname= ticketdata['listings'][j]['sectionName']
			#print(sectionname)
			price= ticketdata['listings'][j]['pricePerProduct']['amount']
			#print(price)
			sql = "Insert into eventinventory (eventid, listingid, quantity, sectionname, price) VALUES (%s,%s,%s,%s,%s)"
			val = (eventid,listingid,quantity,sectionname,price)
			cursor.execute(sql,val)
	except:
  		print("An exception occurred")
db.commit()

