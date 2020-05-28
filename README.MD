
- Following Data Entity classes are available
    - Report - Traffic Report
    - Request - Drones next position request.
    - Traffic - Enum of Traffic Conditions.
    - TubeStation - TubeStation details with lat and lon.

- Following Data Provider classes are available
    - FileProvider - Read the file and returns an array of lines.
    - RequestProvider - Read the drone route and parse the contents to return a list of drone requests.
    - TubeStationProvider - Read the tube.csv file containing tube stations information, parse and produce list of TubeStations.
    - RequestBuffer - Buffer shared between the Dispatcher and Drone. Dispatcher produce Request and Drone consume Request.                             
   
- Dispatcher
    - Simulation starts from the dispatcher.
    - Start the drones with the given star time.
    - Schedule the shutdown of the simulator if shut down time is provided.
    - Send the next position of the Drone.    
- Drone
    - Runs as a separate thread in its own life cycle.
    - When a request is available, process the request and move to the given coordinate.
    - If tube station exists, generate traffic report.
             
- Please follow the below steps to execute the program.
1) Extract the drone-simulator.zip file
2) Open a terminal and navigate to the extracted folder.
3) run "gradlew clean build" to run the test cases and build the jar.
4) run "gradlew runUAT" to run the acceptance test cases.
5) run "gradlew runSimulation" to run the simulation.
6) Program can also be executed with the output jar file as below
    Note: Please keep the drones simulation file in the root class path, and file name should be "<drone_id>".csv
    java -jar drone-simulator-1.0-SNAPSHOT.jar "6043,5937" "2011-03-22 07:47:50" "2011-03-22 08:10:00"
    java -jar drone-simulator-1.0-SNAPSHOT.jar "6043,5937" "2011-03-22 07:47:50" 