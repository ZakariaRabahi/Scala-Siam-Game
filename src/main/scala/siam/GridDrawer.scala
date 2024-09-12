package siam

import gamelib.{Cell, GridPanel}
import java.awt.Graphics2D
import scala.jdk.CollectionConverters.*

// tekenen van grid
object GridDrawer:
  def draw(
            grid: SiamGrid,
            panel: GridPanel,
            cellWidth: Int,
            cellHeight: Int,
            padding: Int
          ): Unit =
    panel.clear()
    var cells: List[Cell] = List()
    grid.forEach((row, column, player) =>
      cells = PlayerCell(
        player,
        column * cellHeight + padding,
        row * cellWidth + padding,
        cellWidth
      ) :: cells
    )
    
// toegevoegde cellen
    cells = RightButton() :: cells
    cells = LeftButton() :: cells
    cells = UpButton() :: cells
    cells = DownButton() :: cells
    cells = RotateButton() :: cells
    cells = MoveButton() :: cells
    cells = AddButton() :: cells
    cells = BepBeweging() :: cells
    cells = OnvDood() :: cells
    cells = OngDraaien() :: cells
    cells = playerdrawing :: cells

    panel.addCells(cells.asJava)
