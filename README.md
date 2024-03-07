# CSC370_Project-GetFit

**GetFit**

Introduction

Starting a fitness regime is a difficult task for anyone. Knowing what goals to set and how to accomplish them takes time and effort, and may result in a plan that isn’t effective for everyone. This app aims to help anybody create an achievable fitness goal and workout plan specific to them. Through the use of AI, a user will be able to find a goal and plan that matches their current level of physical fitness and body type, whether they’re a beginner or experienced. To allow anybody to find a way to improve themselves physically no matter their starting point, this app will create attainable habits and improve the health of its users.

Glossary

Diagnostic System: The back-end processing center which utilizes a random forest algorithm to create the goals for each user.
Planner: The front-end center chooses the fitness routine for each user and uses an interactive calendar display to track a user's progress.
User Measurement: The measurements used in this application for each user are weight, waistline, and resting heart rate.
Available Exercises: The exercises used in this application are chin-ups, sit-ups, and jumping jacks.

User Requirements Definitions

Functional Requirements

The Diagnostic System using the trained random forest will decide a goal for the user based on their max number achieved in the Available Exercises as well as the User’s Measurements.
Rationale: This requirement enables GetFit to create any goals for the user in the first place. Without a base knowledge of the user, it would be impossible to set realistically achievable goals for each different individual.  
The Planner will create a week-long fitness plan with daily exercises for the user to accomplish.
Rationale: To fulfill the goal of the application in facilitating a user’s fitness progress it must create a set of tasks for the user to complete. 
A random forest will be implemented and trained to create standard amounts for each Available Exercise and User Measurement that will inform the Diagnostic System.
Rationale: The random forest is required for establishing an expected value for the amount of each exercise the user would complete based on their User Measurements. The random forest allows the program to create goals for each specific individual creating a more attainable fitness goal for the user. Knowing what User Measurements match the standard exercise amount means the user’s fitness program can be decided on a scale that fits their body type and fitness capabilities. Without this, it would be harder to set accurate goals for a variety of users. 

Non-Functional Requirements

The Diagnostic System will accept chin-ups, sit-ups, and jumping jacks for the fitness assessment.
Rationale: Other exercises would possibly be better for understanding the user’s level of   fitness but due to the dataset used only these will be accepted. 
The Diagnostic System will accept weight, waist size, and resting heart rate for the user’s bodily measurements.
Rationale: Other indicators of health and ability such as age, body fat percentage, and sex of the user would allow for a better understanding of their fitness capabilities, but due to the dataset used only these will be accepted.
The planner must create a schedule based on the current date at the creation of the plan. 
Rationale: The planner being up to date ensures the user can follow the fitness plan in an accurate time scale and that it can be imported onto a calendar.

System Requirements Specifications

System Requirement 1     
     1.1 Description:  When the user starts the app they will input their weight in kilos, their waist measurement in cm, and the amount of beats per minute of their heart rate. The user will then input the maximum amount of sit-ups, chin-ups, and jumping jacks they can do in one set. Using these measurements the diagnostic system will compare the exercise amounts to the expected number from the AI-generated standard. Whether above or below the set standard the system will set goals to increase the totals for every exercise according to the users’ capabilities (eg. a user going from a small amount to moderate, or above average to exceptional).
      1.2 Inputs: Weight, waistline, bpm, and max # of sit-ups, chin-ups, and jumping jacks of the user.
      1.3 Source: Users will self-report their readings and performance.
      1.4 Outputs: A goal number of sit-ups, chin-ups, and jumping jacks.
      1.5 Destination: The Diagnostic System.
      1.6 Precondition: The Diagnostic System has a standard amount for each exercise for every valid user weight, waist, and bpm combination. This comes from the trained random forest regression algorithm.
      1.7 Action: If the user does not input any information the system cannot function and the app will not proceed from the data intake section. According to the relation between a user's reported performance in each exercise and the standard amounts set for a person with their measurements, a goal amount for each will be chosen. For users who are far below or far above their standard, smaller gradual goals will be set as improvement for these groups will be slower. For users within 2 standard deviations from the mean goals will be more consistent with those closer to and above the mean receiving higher goals. 
      1.8 Postcondition: The Planner can receive a set of goals from the Diagnostic System.
      1.9 Side Effects: None. 


    
System Requirement 2
      2.1 Description: Using the goals set by the Diagnostic System, the planner will create a 4-to-8-week training schedule.
      2.2 Inputs:  A goal number of sit-ups, chin-ups, and jumping jacks.
      2.3 Source: Diagnostic System
      2.4 Outputs: A 4 - 8 week training schedule containing sets of each exercise should be completed in each section.
      2.5 Destination: The Planner application display - daily exercise section
      2.6 Precondition: The goal number of each exercise needs to be generated before the planner
      2.7 Action: Based on the inputs, the system will generate a schedule with exercise sections that include the goal amount of each exercise that the user should complete every section to              keep on track. 
      2.8 Postcondition: The user will be able to follow the exercise planner and instructions
      2.9 Side Effects: NONE



System Requirement 3
      3.1 Description: 
      3.2 Inputs: Linnerud Dataset.
      3.3 Sources: scikit-learn.org
      3.4 Outputs: A functional random forest regression algorithm for predicting the number of sit-ups, chin-ups, and jumping jacks a person with a certain weight, waist, and heart rate can complete.
      3.5 Destination: The Diagnostic System.
      3.6 Precondition: A matching dataset of how many sit-ups, chin-ups, and jumping jacks a person with a certain weight, waist, and heart rate can complete.
      3.7 Action: The dataset will be loaded into a multi-output random forest and trained until the output corresponds closely to the dataset. 
      3.8 Postcondition: A prediction algorithm for the number of sit-ups, chin-ups, and jumping jacks a person with a certain weight, waist, and heart rate can complete.
      3.9 Side Effects: None.



System Requirement 4
      4.1 While restricted by the current dataset only chin-ups, sit-ups, and jumping jacks can be accepted by the application. (Usability)
      4.2 A larger variety of exercise data could be utilized to create more complex workout routines and goals for users. (Scalability)



System Requirement 5
      5.1 While restricted by the current dataset only weight, waistline, and resting heart rate can be accepted by the application. (Usability)
      5.2 A larger variety of physical measurements could be utilized to create a more accurate goal for each individual. (Scalability)
      5.3 The types of goals in the application could even extend to physique changes such as weight and body fat percentage. (Scalability)



System Requirement 6
      6.1 Creating an up-to-date calendar ensures the user can follow the fitness plan easily in their daily life. (Usability)
      6.2 Creating a time-accurate schedule for the fitness plan allows the possibility for export to a user’s calendar. (Portability)






**System Modeling**

For the major design considerations, we decided to use layered architecture as our major design architecture throughout the entire project, because it allows replacement of entire layers so long as the interface is maintained, and it is good for maintenance and extensibility. The first layer from the bottom is the database layer, which will have all the datasets in it. The second layer is the diagnostic system, in which our random forest algorithm takes place. It takes the datasets from the database layer and generates the goal amount of every exercise. The third layer is the planner layer, which takes the goal amount of each exercise and generates a planner based on it. The fourth layer will be the display layer that will display information to the user such as the planner just like a calendar for instance.
Class Objects





Sequence Diagrams




**Testing Plan**


For testing purposes an array of nodes with User_Height, User_Weight, User_HR, User_Max_CU, User_Max_SU, and User_Max_JJ set from -200 to 200. 

Unit Testing: For the TreeNode class and RandomForestReg class input data from the training set will be limited to positive integer values with a maximum for each variable depending on it’s specific attribute. The upper boundaries for those variables will be based on the maximum data for each category.

System Testing: 
The Diagnostic system will receive data below zero and over the maximum allowed values to see if there will be output. 
The Planner will receive blank plans and invalid goals to see if there will be a valid output.
The random forest will be tested with data outside of the acceptable boundaries to see if it will accept invalid input.











