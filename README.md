# api

[![Build Status](https://travis-ci.org/Easely/api.svg?branch=master)](https://travis-ci.org/Easely/api)

## About
Easely API provides a HTTP API that allows you to view data from the Harding CS EASEL homework submission site.

## Technical details
The API is written in Java with the Spark Framework. It uses Jsoup to scrape EASEL and retrieve data. MySQL is used for user storage, and redis for caching EASEL information so that we can minimize our traffic to the actual EASEL site.

## Deploying
The Easely API was made to deploy with Dokku.

### Enviornment variables
In order for Easely API to run, certain envoriment variables must first be defined

* JWT_SECRET
* SENTRY_DSN
* DATABASE_URL
* REDIS_URL

## Using the API
The API is rather simple to use.

### Register
POST /api/user/register

#### Request
Headers
* None

Body (JSON)
{
    email: "myemail@gmail.com",
    password: "mypassword",
    easelUsername: "myEaselUsername",
    easelPassword: "myEaselPassword"
}

#### Response
Body (JSON)
{
    jsonWebToken: "userJwt"
}

### Login
POST /api/user/login

#### Request
Headers
* None

Body (JSON)
{
    email: "myemail@gmail.com",
    password: "mypassword"
}

#### Response
Body (JSON)
{
    jsonWebToken: "userJwt"
}

### Get user courses
GET /api/courses
Headers
* Key: Authorization Value: Bearer <JWT>
Body (JSON)
[
  {
    "id": "410",
    "name": "Operating Systems",
    "code": "COMP 310",
    "teacher": "Steve Baber",
    "resources": {
      "Andrew S. Tanenbaum interview": "view?id=13830",
      "PowerPoint Slides": "view?id=13724",
      "UNIX Commands": "view?id=13928",
      "PERL": "view?id=13927",
      "Perl Examples": "view?id=14035",
      "Syllabus": "view?id=13529",
      "Official Grade Book": "view?id=13609"
    }
  }
]
  

### Get course assignments
GET /api/assignments
Headers
* Key: Authorization Value: Bearer <JWT>
Body
[
  {
    "id": "13630",
    "name": "Chapter 2",
    "date": {
      "hour": 23,
      "minute": 59,
      "nano": 0,
      "second": 0,
      "dayOfYear": 249,
      "monthValue": 9,
      "dayOfMonth": 6,
      "dayOfWeek": "WEDNESDAY",
      "month": "SEPTEMBER",
      "year": 2017,
      "chronology": {
        "id": "ISO",
        "calendarType": "iso8601"
      }
    },
    "type": "HOMEWORK",
    "course": {
      "id": "410",
      "name": "Operating Systems",
      "code": "COMP 310",
      "teacher": "Steve Baber",
      "resources": {
        "Andrew S. Tanenbaum interview": "view?id=13830",
        "PowerPoint Slides": "view?id=13724",
        "UNIX Commands": "view?id=13928",
        "PERL": "view?id=13927",
        "Perl Examples": "view?id=14035",
        "Syllabus": "view?id=13529",
        "Official Grade Book": "view?id=13609"
      }
    },
    "attachment": "https:\/\/www.harding.edu\/baber\/classes\/comp310\/Chap2HW.htm",
    "possiblePoints": 10,
    "earnedPoints": 8,
    "graded": true
  }
]
