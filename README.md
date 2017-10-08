![Pitney Bowes](/PitneyBowes_Logo.jpg)

# Pitney Bowes SMB Hackathon

### To identify more customers and automate marketing for right customers


### Use Case

Target Existing Customers: 

To integrate their address book (Validated through identify API) with MailChimp (Marketing automation platform)
Create their customers segment based on the demographic data (using GeoLife) like age group, salary range or gender etc . and automatically create separate lists in mailchimp.
Target these customer segment as per their product like

		new funky shoes range to customers with age group 20-25
		or health monitoring tool to age group 60-70.
		Expensive shoes range to higher salary segment

	By sending campaigns.

Select/ create templates to be sent to specific customer segment.
Pull out reports of those campaigns(who all read etc) and take actions accordingly.

Target New Customers:-

Using Customers existing address take out lat long (using LI API) and then finding lat long of visitors of that address by LI API for marketing.
Create their customers segment based on the demographic data (using GeoLife API) like age group, salary range or gender etc . and create separate address lists for different segments.
Target these customer segment as per their product like

		new funky shoes range to customers with age group 20-25
		or health monitoring tool to age group 60-70.
		Expensive shoes range to higher salary segment

	By sending campaigns---
					   1) physically to their mailing address
					   2) with device ID (generated from LI API for marketing ) send messages on 						  Facebook accounts




### Description
The app showcases PBs capabilities to enrich customer information with demographic information and these additional attributes can be further used to create customer segments and run marketing campaigns with the integration of Mailchimp. 
With LI API for MArketing ne customers can be identified and targetted using physical mailpiece or facebook.


### Prerequisite:

Enter API_KEY and SECRET in the placeholder in build.gradle and build the app to make it run.
