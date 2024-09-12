package siam

import gamelib.*
import utilities.*
import javax.swing.JButton
import java.awt.Graphics2D
import scala.jdk.CollectionConverters.*


object SiamGame:
  def main(args: Array[String]): Unit =

    // Maak een hoofdvenster
    val window = new GUI(width, height, rows, columns, padding)
    // Krijg toegang tot het grid panel
    val panel = window.getGridPanel()
    // Maak gebruik van de utilities
    val utils = new WindowUtils(width, height, rows, columns, padding)

    // Houd een grid bij (zie ook OXOGrid)
    val grid = new SiamGrid(rows, columns)

    // aanmaken drie bergen in midden
    var Mount = new Mountain("mount")
    var Mount2 = new Mountain("mount")
    var Mount3 = new Mountain("mount")

    // toevoegen van de mountains
    grid.add(2, 1, Mount)
    grid.add(2, 2, Mount2)
    grid.add(2, 3, Mount3)

    GridDrawer.draw(grid, panel, (width - 2*padding) / columns, (height - 2*padding) / rows, padding)

    // press listener
    panel.setPressListener((x: Int, y: Int) => {
      val (column, row) = utils.determineCell(x, y)

      //innemen van wat de speler wilt doen
      grid.checkSpell(row, column) // checken welke spreuk
      grid.checkButton(row, column) // welke stap
      grid.checkDirections(row, column) // welke richting


      // als de speler een move heeft gedaan => checken welke en plaatsen op grid
      grid.checkMove(row, column)

      // hertekenen grid zoals het moet
      GridDrawer.draw(grid, panel, (width - 2*padding) / columns, (height - 2*padding) / rows, padding)

    })

    while true do Thread.sleep(0)

