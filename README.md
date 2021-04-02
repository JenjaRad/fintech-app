
![alt text](https://s3-us-west-2.amazonaws.com/medici-prod/root/Medici/public/uploads/custom2_is-embedded-finance-the-next-evolution-in-fintech.jpg?1599833701)


#Problem definition

Create REST web service with the following business requirements:
- Application must expose REST API endpoints for the following functionality:
   - apply for loan (loan amount, term, name, surname and personal id must be provided)
   - list all approved loans
   -  list all approved loans by user
- Service must perform loan application validation according to the following rules and reject application if:
     -  Application comes from blacklisted personal id
     - Filter users and countries in blacklist
     - N application / second are received from a single country (essentially we want to limit number of loan applications coming from a country in a given timeframe)
     - Service must perform origin country resolution using a web service (you should choose one) and store country code together with the loan application. 
      Because network is unreliable and services tend to fail, let's agree on default country code - "lv".