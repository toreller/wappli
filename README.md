# wappli (under construction)

demo banking webapp with spring-boot 2, hibernate

use the repo to <b>bootstrap your webapp</b>

microservices:

- wappli-admin: CRUD operations for customer, bank account administration
- wappli-transfer: money transfer between bank accounts

concepts:
- separate database for each microservice
- java client stubs (call REST service as if it was a local java object)

 
 soon to come:
 - angular client
 - authentication microservice
 - audit (hibernate envers) microservice
 
