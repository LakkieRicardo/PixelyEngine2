# Pixely Engine 2

Pixely Engine 2 is a rewrite of my original PixelyEngine, which was written in Java and did the absolute minimal in terms of what a game engine is: rendering sprites.

Pixely Engine 2 includes spritesheets(called texture sheets), scene management, and a basic renderer. It is written in Scala.

## Build

In order to build this project, run:

```
sbt assembly
cp GameRes/ ./target/scala-2.13/GameRes/
```

If the program is already assembled, run:

```
sbt clean
```

## Controls

- WASD to move the camera around
- F9 to show debug menu
- Tilde/grave/back quote key to cycle through display layers

## Features

- [x] Scene management
- [x] Texture sheets
- [ ] GUI
- [ ] Animations
- [ ] Collision
- [ ] Lighting
- [ ] Particles
- [ ] File management(i.e. compile files into res.bin)