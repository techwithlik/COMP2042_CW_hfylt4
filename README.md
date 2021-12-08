# COMP2042_CW_hfylt4
This is Tan Lik Wei's coursework for the COMP2042 Software Maintenance module (2021).

# Brick_Destroy Game Description
This is a simple arcade video game. The player's goal is to destroy a wall with a small ball.
The game has simple commands:
* SPACE to start/pause the game,
* A/Left arrow to move the player to the left,
* D/Right arrow to move the player to the right,
* ESC to enter/exit pause menu, 
* ALT+SHIFT+F1 to open the console.
* The game automatically pauses if the frame loses focus.

# Key changes made for Maintenance:
* Performed proper spacing and indentation,
* Improved access modifiers. Assigned variables to Final and set fields to Private,
* Typecasting of variables,
* Removed disposable parameters, unused fields and imports,
* Moved declarations from class constructors to inline,
* Enhanced switch cases,
* Break up and extracted large classes like Brick and Wall,
* Organised classes into packages,
* Added meaningful javadocs and comments,
* Model-View-Controller pattern, 
* Used the Maven build tool

# Key Additions:
* Ability to move player's bar left and right using arrow keys,
* A tutorial button, tutorial screen, along with a back button,
* Points system where points are rewarded for bricks destroyed and penalised for losing a ball,
* See live points scored,
* High score screen and ability to set high scores,
* Stopwatch function,
* Additional playable levels,
* Inserted background images and gifs,
* Changed colors of elements to improve user interface