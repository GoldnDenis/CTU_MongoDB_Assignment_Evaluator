# Bachelor thesis at CTU FEL

# KEY POINTS
## Code requirements:
- [?] UI to add new requirements
- [ ] Adaptability
- [ ] go through all files in the directory
- [ ] make an option to make a limit and offset
- [ ] make an option to choose a specific student
- [ ] ALL output is directed into file
## Feedback:
- [ ] Give it using the specific text from the web
- [ ] Try to process the feedback from MongoDB and give it back
## Structure:
- [ ] each student has a directory in format: "f{year, last 2 digits}{semester number}_username"
  - default: semester = 1
- [ ] a directory with username directories
## Connection:
- [ ] execute mongoShell
- [ ] mongosh --port 42222 -u $login -p $password $database $file 
  - $login = personal
  - $password = personal
  - $database = personal
  - $file = "script.js"
## Evaluate the script through parsed file
- [ ] before every query there must be at least one comment line (//)
- [ ] collection:
  - [ ] should be created at least 2 collections
- [ ] insert:
  - [ ] at least once are used both insertOne and insertMany
  - [ ] if student uses old syntax - warn
  - [ ] at least 5 documents should be inserted, i.e. 1 insertOne + 1 insertMany with 4 documents will suffice.
  - [ ] validate json documents
  - [ ] all json documents are non-trivial:
    - [ ] not empty
    - [ ] depth at least 3
    - [ ] at least one embedded object, i.e. {}
    - [ ] at least one embedded array, i.e. []
  - [ ] check if other collection hold inserted references, by "_id"
- [ ] update:
  - [ ] at least once are used updateOne, updateMany and replace
  - [ ] at least once upsert is used
- [ ] find:
  - [ ] Not repeated
  - [ ] Non-trivial, i.e. the first parameter is not empty
  - [ ] At least one logical operator ($and, $or, $not) is used
  - [ ] At least once $elemMatch is used
  - [ ] at least once negative and positive projections are used, i.e. negative - all 0 (except id), positive - all 1
  - [ ] at least once .sort() is used
- [ ] aggregate:
  - [ ] Use at least once each of $match, $group, $sort, $project (or $addFields), $skip and $limit AS stages, i.e. the first $ after ([{
  - [ ] Use at least once each of $sum (or $avg), $count, $min (or $max), $first (or $last) AS aggregators, i.e. depth at least 2
## Testing:
- [ ] Connect to the server
- [ ] Run the script. Check if it does create a database
- [ ] Comment all createCollections
- [ ] Remove all comments
- [ ] Save all output as a log
## Bachelor's thesis:
- [ ] statistics based on logs