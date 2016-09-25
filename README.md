# Maze
You control a character (The @ sign) with the WASD keys and your goal is to collect all the dots on the map. The big O's are ghosts that'll try to eat you, and the hashtags can be picked up to eat a ghost.

It took me a couple of hours to make and really isn't that complicated. But it was fun to make a logic loop, learn about console output, really basic AI (the ghosts) and other stuff like that.

![Screen](http://www.redwarfare.com/proxy.php?image=http%3A%2F%2Fi.imgur.com%2FmE31kyA.png&hash=a6a100d670376e435679956bdd1f5ec4) 

The map is random every time, although it generally looks pretty much the same. Each ghost is also unique, and is given randomly properly when (re)spawned.

![Screen](http://www.redwarfare.com/proxy.php?image=http%3A%2F%2Fi.imgur.com%2FGpwB9oA.png&hash=f8bf6ee29b494b4539b09d4d022fcd79)

Playing the game

1. Download the [latest release](https://github.com/ShepherdJerred/Maze/releases/latest)
2. Create a .bat file with the following

    java -jar maze-1.0.jar -Dfile.encoding=UTF-8
    
    pause
3. Run the batch file
3. Wait for the game to appear (it will stay black for a few seconds after the window opens)
4. Play!

Notes
* It probably only works on Windows
* You'll need to have Java 8 installed
* Don't resize the window after opening or it'll probably break
* It might not be possible to win on every map.. I forgot if walls can generate over the points
* It's probably really buggy

![End](http://www.redwarfare.com/proxy.php?image=http%3A%2F%2Fi.imgur.com%2FrSwkZ0k.png&hash=9d432824472f821a43c46b07629248f8)