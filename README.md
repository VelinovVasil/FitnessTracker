# Fitness Tracker Application - Back-End

# Introduction
This is the Back-End of "Fitness Tracker". In this app users have the convenience to log their fitness meals, add workouts and see the weather forecast before working out. All the needed information for an athlete is in one place.

# Running the Application
1. Clone this repository locally to your computer.
2. Navigate to the root directory of the project in your terminal or command prompt and install the project dependencies by running:

   ```mvn clean install```
4. After running the applicaion, the API will be accessible on port 8080 by default.

# Database Schema
![Screenshot 2024-07-26 084011](https://github.com/user-attachments/assets/c4e01488-f2cd-4b6b-a903-036d51210473)



# Controllers documentation

## AuthenticationController
Handles user authentication and registration.

#### POST /auth/register
Description: Registers a new user.
Request Body: {"username": String, "password": String}
Response: {"token": String} - JWT token for authentication.

#### POST /auth/authenticate
Description: Authenticates a user.
Request Body: {"username": String, "password": String}
Response: {"token": String} - JWT token for authentication.


## ExerciseController
Manages exercise-related operations.

#### GET /exercise/
Description: Retrieves all exercises.
Response: List of exercises.

#### POST /exercise/
Description: Creates a new exercise. Requires ADMIN role.
Request Body: {"name": String, "description": String, "type": String}
Response: No content.

#### GET /exercise/{id}
Description: Retrieves an exercise by ID.
Response: Exercise details.


## LocationController
Manages location-related operations.

#### POST /location/add-location
Description: Adds a new location.
Request Body: {"name": String, "address": String, "latitude": Double, "longitude": Double, "userId": Long}
Response: Location details.


#### DELETE /location/delete/{id}
Description: Deletes a location by ID.
Response: No content.

#### GET /location/user/{userId}
Description: Retrieves all locations by user ID.
Response: List of locations.

#### GET /location/all
Description: Retrieves all locations.
Response: List of locations.

#### GET /location/{id}
Description: Retrieves a location by ID.
Response: Location details.


## RecipeController
Handles CRUD operations for recipes.

#### GET /recipe/
Description: Retrieves all recipes.
Response: List of recipe summaries.

#### POST /recipe/create
Description: Creates a new recipe.
Request Body: Recipe details.
Response: Recipe details.

#### GET /recipe/get/{userId}
Description: Retrieves all recipes by user ID.
Response: List of recipe summaries.

#### GET /recipe/{id}
Description: Retrieves a recipe by ID.
Response: Recipe details.

#### DELETE /recipe/{id}
Description: Deletes a recipe by ID.
Response: No content.

#### PUT /recipe/{id}
Description: Updates a recipe by ID.
Request Body: Recipe details.
Response: No content.


## UserController
Manages user-related operations.

#### GET /user/
Description: Retrieves all users. Requires ADMIN role.
Response: List of users.

## WeatherController
Provides weather information.

#### GET /weather/current
Description: Retrieves current weather information.
Request Param: "q" - Location query.
Response: Current weather details.

## WorkoutController
Manages workout-related operations.

#### GET /workout/
Description: Retrieves all workouts.
Response: List of workout summaries.

#### POST /workout/create
Description: Creates a new workout.
Request Body: Workout details.
Response: No content.

#### GET /workout/get/{userId}
Description: Retrieves all workouts by user ID.
Response: List of workout summaries.

#### GET /workout/{id}
Description: Retrieves a workout by ID.
Response: Workout details.

#### DELETE /workout/{id}
Description: Deletes a workout by ID. Requires ADMIN role or ownership.
Response: No content.

#### PUT /workout/{id}
Description: Updates a workout by ID.
Request Body: Workout details.
Response: No content.

# Scheduled job: Automatic email reminder
The application sends motivational emails to all of its users everyday at 9:00 am.

![image_123655411](https://github.com/user-attachments/assets/4d984bf9-39ea-4e8a-ae2d-aaca2b4d6d32)
