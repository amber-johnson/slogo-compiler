# Simulation API Analysis

Contributors:
* Amber Johnson, ajj18
* Eric Han, eh174
* Ha Nguyen, hn47
* Michael Castro, mc546

We analyzed Group 04's Simulation API.

### API Categorization
* `public Simulation(Grid grid)`: external API
* `public abstract void analyzeCells()`: internal API
* `public void updateCells()`: external API
* `public Grid getGrid()`: external API
* `public void analyzeCells()` in `PredatorPreySimulation`, `SpreadingOfFireSimulation`, `GameOfLifeSimulation`, 
`SegregationSimulation`, and `PercolationSimulation`: external API


### What service we wish the API provides 
* Editing parameters for the simulation when the simulation is already running (e.g. edge types, thresholds, neighborhoods, number of states)
* Changing the state of a specific cell mid-way through a simulation

### Simplified API description of internal & external API 

The abstract class is the `Simulation` class, while the concrete classes are `PredatorPreySimulation`, `SpreadingOfFireSimulation`, `GameOfLifeSimulation`, `SegregationSimulation`, and `PercolationSimulation`. The concrete classes are not abstractions because they are specifically implementing the rules for each of the simulations. 

Externally, when the frontend client selects an existing  simulation to display, all the information is read in from an XML file. Then, the Game class is the controller that manages communication between the front-end client and the Simulation API. The Game class does so by calling the `analyzeCells` method and the `updateCells` method in the Simulation API in order to loop through the simulation. After this, the Game class will call `getGrid` from the Simulation API in order to finally pass the grid to be displayed to the front-end client. This process is comparable to the service at a restaurant; the front-end client is the customer, who asks the Simulation API, which is the chef, to prepare the food (the new, updated Grid based on Simulation rules); however, the customer does not speak directly with the chef; instead, they go through the waiter, which is the Game class, who then relays the request back to the Chef and brings the prepared food out to the customer afterward. 

Internally, when a new developer joins the backend team and adds a new possible kind of simulation that can be available for the front-end to choose, the developer simply has to implement the `analyzeCells` method in the internal API in a distinct class, which implements the rules of that Simulation. This is like adding a new event onto our calendar; we only need to figure out the details of that event, then append it to the existing calendar of events, and nothing changes the previous events. 