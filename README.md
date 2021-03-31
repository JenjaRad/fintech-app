
![alt text](https://lh3.googleusercontent.com/proxy/Uu3MVXvK0jFbqc5W44d-tS1cRCMq5FwmzfDH0OlDEmotLgJY96fpEOK0tXRLeab1wiCQmJrMNRLrY-uyLkW57oRxDmrE3LdEMYHcStlurPRdhcNDXTUVl8Z1zDV-0MUBOg)


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