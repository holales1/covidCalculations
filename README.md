# Coronavirus Coding Challenge

### Summary

This challenge consists in writing a Java program which outputs the number of total monthly COVID-19 cases for a given
country and year.

### Instructions

The resulting program should:

1. Have a command line interface.
2. Accept two arguments:
   - An alpha-2 country code (ISO 3166): either "US" or "GB".
   - A 4-digit year, e.g. "2020".
3. Read data from the following publicly-available APIs:
   - [United States](https://covidtracking.com/data/api/version-2), for example from [`https://api.covidtracking.com/v2/us/daily.json`](https://api.covidtracking.com/v2/us/daily.json).
   - [United Kingdom](https://coronavirus.data.gov.uk/details/developers-guide/main-api), for example from [`https://api.coronavirus.data.gov.uk/v1/data?filters=areaType=overview&areaName=United Kingdom&structure={"date":"date","newCasesByPublishDate":"newCasesByPublishDate"}`](https://api.coronavirus.data.gov.uk/v1/data?filters=areaType=overview&areaName=United+Kingdom&structure=%7B%22date%22%3A%22date%22%2C%22newCasesByPublishDate%22%3A%22newCasesByPublishDate%22%7D)
     (note that, in this case, the query parameters have characters that require URL encoding, and that the body's
     response is compressed with GZIP).
   It is important that the code be prepared in a way that adding new data sources (i.e. diffent APIs) is easy.
4. Print to standard output the total number of COVID-19 cases reported for each month. Begin with a simple format of
   your choice and, if time allows, for the cherry on top, try plotting the same data using a horizontal bar chart with
   UTF-8 characters such as "▇" (for this, you can assume a fixed terminal width, e.g. 200 columns).

### Starting Point

A Dockerfile has been prepared to simplify development. All it does is use Maven to build a JAR, then run it. To build
and run the program, all you need is to install Docker (version 23.0 or newer) and run the following commands:

```sh
docker build -t corona .
docker run -it corona US 2020
```

All runtime dependencies required to complete the challenge should already be present in the POM. That said, if you
consider other external libraries could be of help, there are no restrictions in that regard.

### Time Limit

You are not expected to invest more than 4 hours in this exercise. If you have not completed it after this time, don't
worry, simply document what remaining steps you would have taken had you been given more time, and submit your partial
work.

### Evaluation Criteria

We will be looking for code which (provably) accomplishes its goal while remaining simple, maintainable, extensible,
clean, consistent... Written in an idiomatic manner and with good overall organization.

### Submission

When you are done, please provide a public link to your Git repository. You may choose to host it wherever you like, be
it GitHub or some other service.

As the last step, please include with your submission an answer to the following open-ended question: in your view, how
would you rank the following software characteristics in order of importance (from most important to least important)?
- efficiency and performance
- maintainability
- reliability
- compatibility and portability
- correctness
