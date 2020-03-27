# MobiLabTestAssignment
This project is test assignment for MobiLab internship opportunity. It is Android application with functionality to create and edit shopping lists. 

## The goal of the application
Application allows users to create shopping lists(or any other lists) and add items to created lists. User can delete created items or mark them as done. All user lists are stored in cloud. To delete item or list user must perform long click on item/list.

## How to compile and run application
To compile and run this application Android Studio is needed. After importing this project to Android Studio, it allows to run it on emulator or real device with Android 5.0 or higher.

## How to run tests for this application
This application doesn't have currently test coverage. Application was tested manually :)

## Architecture of the application
All data for this application is stored inside viewModel class that handles everything related to data. ViewModel class is also used to save and read data from Google Firebase Database. Application itself has one activity and multiple fragments that handle UI interactions.