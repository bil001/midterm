###Workflow  
  
*MON Afternoon / TUES morning

- [ ] Create class diagram

- [ ] Create project classes  
  - Host
    - Id, Name, Email, Daily rates, List of Reservations, State, City, ZIP code  
  - Guest  
    - Id, Name, Email, Phone, State  
  - Reservation
    - Date check-in, Date check-out, Guest id, Total cost
- [ ] Create project framework  
  - Guest, host, reservation repos 
  - Guest, host, reservation services
  - UI & View  
  - App  
- [ ] Add spring XML dependencies 
- [ ] Add basic repository methods
  - Read & write to file
  - Serialize/Deserialize
  - findAll
  
*TUES Afternoon / WED Morning
- [ ] Add basic view / ui methods
  - ReadInt
  - ReadString
  - Setup main menu
- [ ] Work on view reservations by host
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
  - Parameters:
    - Guest
    - Host
  - Validations:
    - Ensure date not in past
  
*FRI Afternoon / Weekend
-[ ] Work on bug fixing and polish
