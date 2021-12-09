# COMP2042_CW_hfylt4
This is Tan Lik Wei's (Student ID: 20208762) coursework for the COMP2042 Software Maintenance module (2021).

# Brick_Destroy Game Description
This is a simple arcade video game. The player's goal is to destroy a wall with a small ball. <br />
The game has simple commands:
* SPACE to start/pause the game,
* A/Left arrow to move the player to the left,
* D/Right arrow to move the player to the right,
* ESC to enter/exit pause menu, 
* ALT+SHIFT+F1 to open the console.
* The game automatically pauses if the frame loses focus.

# Key changes made for Maintenance:
* Performed proper spacing and indentation,
* Improved access modifiers: set fields to Private and Protected. Applied 'Final' to variables,
* Typecasting of variables,
* Removed disposable parameters, unused fields, methods, and imports,
* Moved declarations from class constructors to inline,
* Changed inaccurate variable and method names like MENU_TEXT to EXIT_TEXT,
* Enhanced switch cases,
* Break up and extracted large classes like Brick and Wall to apply SOLID principles,
* Organised classes into packages,
* Added meaningful javadocs and comments,
* Model-View-Controller pattern, 
* Used the Maven build tool,
* Implemented Junit tests

# Key Additions:
* Ability to move player's bar left and right using arrow keys,
* A tutorial button that leads to a tutorial screen where information on how to play the game is displayed, along with a back button,
* Points system where points are rewarded for bricks destroyed and penalised for losing a ball,
* See live points scored,
* High score screen that displays after the game ends and ability to set and store high scores,
* Stopwatch function,
* Additional playable levels,
* Inserted background images and gifs,
* Changed colors of elements to improve user interface