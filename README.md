## Required:
- Date criterion ✅
- Interlink criterion:✅


    aggregate $lookup - find local fields and foreign fields (opt find there values).

- Big table ✅
---  
## Recommended minor adjustments:
- Configuration of a DateValue in a document ✅
- Evaluate only queries with existent collections ✅
- Find queries could look for a non-existent document (it just returns an empty result) ✅
- Comment criterion shouldn't display all its queries ✅
---  
## Potential refactoring/redesigning:
- Criteria ✅
- Criteria evaluator ✅
- Parser:
  - Central class - state machine 🟨
  - States 🟨
  - Pre-processor
---  
## Recommended thesis structure:
* ### Abstract
    A concise explanation of the project (cca 5-10 sentences)
---  
* ### Introduction:
    Motivation. Why even solve this problem?
---  
* ### Research:
    Competition analysis, i.e. projects that solve similar/the same problem. Should answer why even work on it if it is already solved.
---  
* ### Analysis:
  * Requirements elicitation
  * Functional/Non-functional requirements
  * Data analysis (e.g. class diagram, component diagram (architecture showcase), sequence diagrams, use-cases etc).
---  
* ### Implementation description:
  * Validation
  * Security
  * Problems and Solutions
---  
* ### Testing:
    A proof that the described implementation works
---  
* ### Conclusion:
    What was not implemented, but could be in the future. A space for improvements and extension.