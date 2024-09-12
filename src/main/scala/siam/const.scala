package siam

// constanten voor grid
val width = 700
val height = 700
val rows = 5
val columns = 5
val padding = 200

// voor bijhouden keuzes speler
var PlayerDirection: String = ""
var GameButton: String = ""
var spellButton: String = ""

// bijhouden welke speler nu speelt
var currentPlayer: Element = Rhino(PlayerDirection)
var winner: Element = Rhino("")
// aantal in het spel mogelijk
var numberOfRhinos: Int = 5
var numberOfElephants: Int = 5
var numberOfMountains: Int = 3

// variabels voor spreuken
var RhinoBeperkt = false
var RhinoDraai = false
var RhinoDood = false

var ElephantBeperkt = false
var ElephantDraai = false
var ElephantDood = false



