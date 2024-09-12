package siam

import gamelib.{AssetsLoader, Cell}
import java.awt.Graphics2D


class PlayerCell(player: Element, x: Int, y: Int, size: Int) extends Cell:
  override def draw(g: Graphics2D): Unit =

// afhankelijk van welke element je bent
    if player.isInstanceOf[Rhino] then
      
      // afhankelijk van jouw richting 
      player.dir match {

        case "up" => g.drawImage(AssetsLoader.loadImage("neushoorn_up.png"), x, y, size, size, null)
        case "down" => g.drawImage(AssetsLoader.loadImage("neushoorn_down.png"), x, y, size, size, null)
        case "left" => g.drawImage(AssetsLoader.loadImage("neushoorn_left.png"), x, y, size, size, null)
        case "right" => g.drawImage(AssetsLoader.loadImage("neushoorn_right.png"), x, y, size, size, null)
        case _ =>
      }
      
      
// element check
    else if player.isInstanceOf[Elephant] then
      // direction check
      player.dir match {

        case "up" => g.drawImage(AssetsLoader.loadImage("olifant_up.png"), x, y, size, size, null)
        case "down" => g.drawImage(AssetsLoader.loadImage("olifant_down.png"), x, y, size, size, null)
        case "left" => g.drawImage(AssetsLoader.loadImage("olifant_left.png"), x, y, size, size, null)
        case "right" => g.drawImage(AssetsLoader.loadImage("olifant_right.png"), x, y, size, size, null)
        case _ =>
      }
      
  // mountain heeft geen richting dus direct
    else if player.isInstanceOf[Mountain] then
      g.drawImage(AssetsLoader.loadImage("mountain.png"), x, y, size, size, null)
