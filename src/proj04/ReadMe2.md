## CSC123 — Shape Project v2
### TL;DR
### Goal

Build an animated ASCII shape system demonstrating **inheritance, overriding, polymorphism, and collision logic**.
---

## Core Architecture
### Class Hierarchy
Shape
├── Point
│   └── Circle
│       ├── Sphere
│       └── Cylinder
└── Square
    ├── Rectangle
    └── Cube

### Design Rationale
* **Circle extends Point**
  A circle has a single center location + radius → belongs in the Point branch.
* **Sphere & Cylinder extend Circle**
  They inherit “center + radius” and add volume or height.
* **Square extends Shape (not Point)**
  A square is defined by corners, not a single center → separate branch.
---
## Core Behaviors
### 1. Animation
* `Shape[] shapes`
* Loop calls `tick()` and `draw()` polymorphically.
* Motion controlled by `MovePattern` (strategy object).
---
### 2. Color System
* Each shape has a `Color` (ANSI-based).
* Color drives both **rendering and collision rules**.
---
### 3. Collision Rule
Implemented via: equals()

* Same color → `equals()` returns `true` → shapes pass through.
* Different color → `false` → bounce (velocity reversal).

Collision check occurs inside `tick()`.
---
### 4. Overriding Demonstrations
| Concept               | Where          | Why it Matters                   |
| --------------------- | -------------- | -------------------------------- |
| `getName()`           | All subclasses | Basic overriding                 |
| `equals()`            | Shape          | Changes runtime behavior         |
| `draw()`              | Sphere         | Inherited vs overridden behavior |
| `footprintW/H()`      | Rectangle      | Affects collision bounds         |
| `toString()` chaining | Cylinder       | Demonstrates super-call chain    |

Example inheritance chain:

Cylinder.toString()
 → Circle.toString()
   → Point.toString()
---
## Visual Behavior Groups
| Color    | Shapes         | Interaction             |
| -------- | -------------- | ----------------------- |
| 🔴 RED   | Circle, Sphere | Pass through each other |
| 🟢 GREEN | Square, Cube   | Pass through each other |
| 🔴 vs 🟢 | Any            | Bounce                  |
---
## Concepts Demonstrated
* Inheritance tree structure
* Method overriding
* Polymorphism via `Shape[]`
* Strategy pattern (`MovePattern`)
* Dynamic dispatch
* Collision detection
* Encapsulation
* Runtime behavior driven by equality
---
## What Students Must Restore/Test
* `getName()` override
* `equals()` override
* `Sphere.draw()` override
* `Rectangle.footprintW/H()` override
* `Rectangle.draw()` override
Visual consequences make mistakes obvious.
---
## Compile & Run
javac *.java
java Driver
---

## Big Idea
This project is not about ASCII art.
It is about:
* Seeing inheritance visually.
* Seeing overriding change runtime behavior.
* Seeing polymorphism operate across unrelated shapes.
* Watching equality affect physics.

If it moves wrong, something is wrong.
That’s intentional.

