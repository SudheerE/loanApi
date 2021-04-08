# Environment:
- Java: 1.8
- Spring Boot: 2.4.4

# Request:
```json
{
"ssnNumber": "111-11-1111",
"loanAmount": 150000.00,
"annualIncome": 500000.00
}
```

# Response:
```json
{
	"loanStatus": "ELIGIBLE",
	"sanctionedAmount": 250000.00,
	"comments": "Success"
}
````

#Loan Api
- Loan Request and Response can be accessed using swagger url: http://localhost:8080/swagger-ui/ 
- Credit score is generated using random numbers between 600 to 850
- H2 database is used to store the data in file system
