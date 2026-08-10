## Table of Contents
- [Introduction](#introduction)
- [Technology Stack](#technology-stack)
- [Projects](#projects)

## Introduction
These projects are intended to create java backend application.

## Technology Stack
List the major technologies, libraries, or frameworks used in your Java project.
- Java 17 (or your version)
- Maven/Gradle for build automation
- Spring Boot
- Any external libraries or APIs
- SQL

For Cloning the repository use the following:
```bash
   git clone https://github.com/your-username/project-name.git
```

# Projects

1. [To Do List](#to-do-list)
2. [GitHub Activity](#github-activity)

# To Do List

## GET Request
Pre URI: 
```
http://localhost:8080/todohub
```
### All tasks:
```
/api/task/tasks
```
### Specific Task by id:
```
/api/task/task?id={id}
```
### List All done tasks:
```
/api/task/done_tasks
```
### List All tasks which are marked as not done:
```
/api/task/not_done_tasks
```
### List All tasks which are marked as in progress:
```
/api/task/in_progress_tasks
```
## PUT Request
### Update Task with id and body:
```
/api/task/update_task/{id}
```

#### Body:
```json
{
  "id": 0,
  "goal": "string",
  "description": "string",
  "created": "2026-08-10T13:57:46.013Z",
  "updated": "2026-08-10T13:57:46.013Z",
  "status": "DONE"
}
```
### Update Task status by id     
```
/api/task/update_task_status/{id}/{status}
```
## POST Request
### Create a new Task:
```
/api/task/create_task
```
## DELETE Request
### Delete Task by id
```
    /api/task/delete_task
```

# GitHub Activity

## GET Request
Pre URI: 
```
http://localhost:8080/git_activity_hub
```
### All tasks:
```
/api/git/activity/{user_name}
```
