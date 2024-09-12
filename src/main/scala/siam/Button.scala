package siam

import gamelib.{AssetsLoader, Cell}

import java.awt.Graphics2D

// button class voor alle knoppen in het spel

abstract class Button extends Cell {
  var x: Int // positie x
  var y: Int // positie y
  var path: String // photo
  var direction: String // richting voor richting buttons

  var cellDimensions = (width - 2*padding) / columns

  override def draw(g: Graphics2D): Unit =
    g.drawImage(AssetsLoader.loadImage(path), x, y, cellDimensions, cellDimensions, null)
    
  def cellToPixel(c: Int): Int = {
    return cellDimensions * c + padding
  }
  // voor player image
  def flip(): Unit = {
    if path == "player2.png" then
      path = "player1.png" else
      path = "player2.png"
  }
  
}