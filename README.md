# BankSystem (Pet Project)

Java RESTful API for money transfers between customers accounts

### Technologies
- Spring Boot
- PostrgreSQL


### How to run
```sh
To launch classes Application.java in servecies customerService, accountService and commonService
```

Services commonService, accountService and customerService   start on localhost ports 8080, 8081 and 8082 respectively. 
Postgre database initialized with some sample user.


### Available Services

## Сustomer Service
| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| GET | /customer/{id} | get customer by id | 
| GET | /customer/all | get all customers | 
| POST | /customer | create new customer | 
| PUT | /customer/change/{id}| update customer info | 
| DELETE | /customer/{id} | delete customer by id | 

### Sample JSON for Сustomer Service
##### Create customer : 
```sh
{  
  "name":"Alex",
  "surname":"Brown"
} 
```

## Account Service
| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| GET | /bill/{id} | get bill by id | 
| GET | /bill/get-by-customer-id/{customerID} | get bill by customer id | 
| POST | /bill/create/{customerID} | create bill for exact customer | 
| POST | bill/delete/{id} | delete bill by id | 
| PUT | "bill/adjustment/{id} | commit adjustment for bill by id | 
| PUT | "bill/payment/{id} | commit payment for bill by id | 

### Sample JSON for Account Service
##### Create bill : 
```sh
{  
  "customerID":"1",
  "sum":"1000.00"
} 
```

##### Commit adjustment : 
```sh
{  
  "adjustment":"500.00",
} 
```

##### Commit payment : 
```sh
{  
  "payment":"500.00",
} 
```

## Common Service
| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| GET | /common/info/{id} | get all info about customer by id | 
| PUT | /common/transfer-for-one-customer/{transfer} | commit cash transfer between two bills belonged to one customer  | 
| PUT | /common/transfer-for-two-customers/{transfer} | commit cash transfer between two bills belonged to two customers  | 

### Sample JSON for Common Service
##### Commit transfer for one customer : 
```sh
{  
  "customerID":"1",
  "decreasedBillID":"1",
  "increasedBillID":"2"
} 
```

##### Commit transfer for two customers : 
```sh
{  
  "firstCustomerID":"1",
  "secondCustomerID":"2",
  "decreasedBillID":"3",
  "increasedBillID":"2"
} 
```
