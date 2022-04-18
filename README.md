###Workflow  
  
*MON Afternoon / TUES morning

- [ ] Create class diagram
  - Estimated Time: 30 min
  - Actual Time:

- [ ] Create project classes  
  - Estimated Time: 1 hr
  - Actual Time: 
  - Host
    - Id, Name, Email, Daily rates, List of Reservations, State, City, ZIP code  
  - Guest  
    - Id, Name, Email, Phone, State  
  - Reservation
    - Date check-in, Date check-out, Guest id, Total cost
- [ ] Create project framework  
  -Estimated time: 1 hr  
  -Actual time:  
        - Guest, host, reservation repos   
        - Guest, host, reservation services  
        - UI & View  
        - App  
- [ ] Add spring XML dependencies
  - Estimated Time: 15 min
  - Actual Time:  
- [ ] Add basic repository methods
  - Estimated time: 1 hr
  - Actual time: 
  - Read & write to file
  - Serialize/Deserialize
  - findAll
  
*TUES Afternoon / WED Morning
- [ ] Add basic view / ui methods
  - Estimated time: 2 hrs
  - Actual time:  
  - ReadInt
  - ReadString
  - Setup main menu
- [ ] Work on view reservations by host
  - Estimated time: 3 hrs
  - Actual time: 
  - Find reservations by host
    - Sort reservations by date
  - Verify fields in service
    - Not null
  - Print reservations from view 
    - Show total income based for host after their reservations
      - (If possible maybe save this until the end)  
  - Test repo and domain as you go  

*WED Afternoon / THURS Morning
- [ ] Work on add reservation  
  - Estimated time: 3 hrs
  - Actual time:  
  - Create add method in repository
    - Parameters:
      - Guest 
      - Host 
      - Start Date
      - End Date
    -Outputs reservation and adds to file
  - Validate fields in reservation service
    - Ensure no null fields
    - Ensure start date is in future
    - Ensure start and end date are in sequence
    - Ensure guest and host id exist
    - Ensure no dates overlap
  - Collect guest and host id from view
  - Call method from controller
  - Print summary from view
- [ ] Work on update reservation
  - Estimated time: 3 hrs
  - Actual time: 
  - Create edit method in repo
    - Parameters:
      - Guest
      - Host
      - New start and end date
  -Validations:
      - Start date must be in future
      - Start and end date must be in sequence
      - Dates must not overlap
      - Guest and host must exist
  -Get user inputs from view
  -Pass into controller

*THURS Afternoon / FRI Morning  
- [ ] Work on delete reservation
  - Estimated time: 2 hrs
  - Actual time:  
  - Parameters:
    - Guest
    - Host
  - Validations:
    - Ensure date not in past
  
*FRI Afternoon / Weekend  
-[ ] Work on bug fixing and polish
