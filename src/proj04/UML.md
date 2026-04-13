CSC123 — ASCII Shape Parade (Inheritance UML)

                    ┌───────────────────────────────┐
                    │             Shape             │
                    ├───────────────────────────────┤
                    │ # color : Color               │
                    │ # sx, sy : int                │
                    │ # vx, vy : int                │
                    ├───────────────────────────────┤
                    │ + getName() : String          │
                    │ + getColor() : Color          │
                    │ + setColor(c: Color) : void   │
                    │ + equals(obj: Object) : bool  │
                    │ + hashCode() : int            │
                    │ + footprintW() : int          │
                    │ + footprintH() : int          │
                    │ + draw(c: AsciiCanvas) : void │
                    │ + tick(...) : void            │
                    └───────────────┬───────────────┘
                                    │
                ┌───────────────────┴───────────────────┐
                │                                       │
    ┌───────────────────────────────┐     ┌───────────────────────────────┐
    │             Point             │     │             Square            │
    ├───────────────────────────────┤     ├───────────────────────────────┤
    │ # x, y, z : double            │     │ # side : double               │
    ├───────────────────────────────┤     ├───────────────────────────────┤
    │ + getName() : String          │     │ + getName() : String          │
    │ + setPoint(x,y,z) : void      │     │ + setSide(s: double) : void   │
    │ + draw(c) : void              │     │ # sInt() : int                │
    │ + footprintW/H() : int        │     │ + footprintW/H() : int        │
    └───────────────┬───────────────┘     │ + draw(c) : void              │
                    │                     └───────────────┬───────────────┘
    ┌───────────────────────────────┐                     │
    │             Circle            │                     │
    ├───────────────────────────────┤         ┌───────────┴───────────┐
    │ # radius : double             │         │                       │
    ├───────────────────────────────┤ ┌───────────────────────┐ ┌───────────────────────┐
    │ + getName() : String          │ │       Rectangle       │ │         Cube          │
    │ + setRadius(r: double) : void │ ├───────────────────────┤ ├───────────────────────┤
    │ # rInt() : int                │ │ # height2 : double    │ │ # depth : double      │
    │ + footprintW/H() : int        │ ├───────────────────────┤ ├───────────────────────┤
    │ + draw(c) : void              │ │ + getName() : String  │ │ + getName() : String  │
    └-───────────┬───────────┬──────┘ │ # hInt2() : int       │ │ # dInt() : int        │
                 │           │        │ + footprintW/H():int  │ │ + footprintW/H() : int│
                 │           │        │ + draw(c) : void      │ │ + draw(c) : void      │ 
                 │           │        │ + draw(c) : void      │ │ + draw(c) : void      │
                 │           │        └───────────────────────┘ └───────────────────────┘
    ┌──────────────────┐ ┌──────────────────┐ 
    │      Sphere      │ │     Cylinder     │ 
    ├──────────────────┤ ├──────────────────┤
    │ (no new fields)  │ │ # height : double│
    ├──────────────────┤ ├──────────────────┤
    │ + getName()      │ │ + getName()      │
    │ + draw(c)(filled)│ │ + draw(c)        │
    └──────────────────┘ └──────────────────┘


Legend:
- Solid inheritance arrows implied by layout: subclass under superclass.
- Visibility:  + public, # protected
- Color is an enum (Color.RED, Color.GREEN, ...), used by Shape.equals() for collisions.
