### Workflow  
  
* MON Afternoon / TUES morning

- [ x ] Create class diagram
  - Estimated Time: 30 min
  - Actual Time: 15 min

- [ x ] Create project classes   
   - Estimated Time: 1 hr
  - Actual Time: 30 min
  - Host
    - Id (String)
    - First Name (String)
    - Last Name (String)
    - Email (String)
    - Weekday Rate (BigDecimal)
    - Weekend Rate (BigDecimal)
    - State (String)
    - City (String)
    - ZIP (int)
    
  - Guest  
    - Id (int)
    - First Name (String)
    - Last Name (String)
    - Email (String)
    - Phone (String)
    - State (String)
  - Reservation
    - Date check-in (LocalDate)
    - Date Check-out (LocalDate)
    - Guest Id (int)
    - Total Cost (BigDecimal)
    - Host (Host)
    - Guest (Guest)
- [ x ] Create project framework  
  -Estimated time: 1 hr  
  -Actual time: 45 min
- Repos:
  - Guest   
  - Host 
  - Reservation 
- Services:
  - Guest
  - Host
  - Reservation
- UI & View  
- App  
- [ x ] Add spring XML dependencies
  - Estimated Time: 15 min
  - Actual Time:  5 min
- [ x ] Add basic repository methods
  - Estimated time: 1 hr
  - Actual time: 30 min
  - Read & write to file
  - Serialize/Deserialize
  - findAll
  
* TUES Afternoon / WED Morning
- [ x ] Add basic view / ui methods
  - Estimated time: 2 hrs
  - Actual time:  1 hr
  - ReadInt
  - ReadString
  - Setup main menu
- [ x ] Work on view reservations by host
  - Estimated time: 3 hrs
  - Actual time: 3 hrs
  - Find reservations by host
    - Sort reservations by date
  - Verify fields in service
    - Not null
  - Print reservations from view 
    - Show total income based for host after their reservations
      - (If possible maybe save this until the end)  
  - Test repo and domain as you go  

* WED Afternoon / THURS Morning
- [ x ] Work on add reservation  
  - Estimated time: 3 hrs
  - Actual time: 4 hrs
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

* THURS Afternoon / FRI Morning  
- [ ] Work on delete reservation
  - Estimated time: 2 hrs
  - Actual time:  
  - Parameters:
    - Guest
    - Host
  - Validations:
    - Ensure date not in past
  
* FRI Afternoon / Weekend  
- [ ] Work on bug fixing and polish
