package siam
// button voor rechts
class RightButton() extends Button{
  var x = cellToPixel(3) //480
  var y = cellToPixel(6)//555
  var path = "right.png"
  var direction = "right"
  
}

// button voor links
class LeftButton() extends Button{
  var x = cellToPixel(1)//230
  var y = cellToPixel(6)//555
  var path = "left.png"
  var direction = "left"
  
}

// button voor onder
class DownButton() extends Button{
  var x = cellToPixel(2) //395
  var y = cellToPixel(7) //555
  var path = "down.png"
  var direction = "Down"
  
}

// button voor boven
class UpButton() extends Button{
  var x = cellToPixel(2) //315
  var y = cellToPixel(5) //555
  var path = "up.png"
  var direction = "Up"
  
}

// button voor rotatie
class RotateButton() extends Button{
  var x = cellToPixel(5)
  var y = cellToPixel(0)
  var path = "rotate.png"
  var direction = ""
  
}

// button voor te zeggen dat je wilt bewegen
class MoveButton() extends Button{
  var x = cellToPixel(5)
  var y = cellToPixel(1)
  var path = "move.png"
  var direction = ""
  
}

// button om element toe te voegen
class AddButton() extends Button{
  var x = cellToPixel(5)
  var y = cellToPixel(2)
  var path = "add.png"
  var direction = ""

}

// spreuk beperkte beweging
class BepBeweging() extends Button{
  var x = cellToPixel(4)
  var y = cellToPixel(5)
  var path = "beperkte_bewegingsvrijheid.png"
  var direction = ""

}

// spreuk onverwacht dood
class OnvDood() extends Button{
  var x = cellToPixel(4)
  var y = cellToPixel(6)
  var path = "onverwachte_dood.png"
  var direction = ""

}

// spreuk draaien
class OngDraaien() extends Button{
  var x = cellToPixel(4)
  var y = cellToPixel(7)
  var path = "ongecontroleerd_draaien.png"
  var direction = ""

}

// aanwijzen welke speler
class Current() extends Button{
  var x = cellToPixel(-2) 
  var y = cellToPixel(-2)
  var path = "player2.png"
  var direction = ""
  cellDimensions = 2 * (width - 2*padding) / columns
  
}

// aangemaakt button
var playerdrawing: Button = Current()
