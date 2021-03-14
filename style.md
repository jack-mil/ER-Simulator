## Code Style Guide ##
For consistency in our code, the following style is proposed:

### Java Naming Conventions ##
  - Classes: generally nouns with UpperCamelCase words e.g. `ImageSprite`, `Patient`.
  - Methods: generally verbs with lowerCamelCase e.g. `getStatus();`, `runBackgroundTask();`.
  - Variables: short, descriptive lowerCamelCase e.g. `bool empty;`, `int iterationCount;`.
  - Constants: loud snake case `static final int MIN_WIDTH = 10;`, `static final bool DEBUG = true;`
  - Method parameters should be short if possible. Also see [Comments](#comments).

### Formatting Style ###
- Indentation should be set to tabs in your editor
- Line length should try to stay under 80 columns if possible
- To save some screen space, place opening curly braces on the same line as a declaration.
  ```java
  if (x < y) {
      // some code
  }
  ```
- Spaces should be used around binary operators e.g. `int b = x + b;` not `int b=x+b;`
- Methods should be kept under 40-50 lines if possible.

### Comments ###
- Omit needless words
- Use the active voice
- JavaDoc comments can be used to document method and class declarations. Most IDE's will use the comment in intelli-sense windows (hover over names).
- Some editors will even autocomplete JavaDoc comments with @return and @param lines.
- Inline comments should be used to explain any potentially confusing or unique circumstances.