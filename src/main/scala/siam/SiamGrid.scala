package siam

// Klasse om een grid bij te houden
class SiamGrid(rows: Int, columns: Int):
  private var matrix: Array[Array[Element]] =
    Array.fill(rows, columns)(Empty)

  // delete zorgt om element weg te halen van grid
  def delete(row: Int, column: Int): Unit =
    matrix(row)(column) = Empty

  // procedure om te checken welk speler de current player is, (wordt gebruikt wanneer van grid gehaald)
  def checkElement(row: Int, column: Int, player: Element): Unit = {
    // als het een rhino is
    if matrix(row)(column).isInstanceOf[Rhino] then
      numberOfRhinos += 1 // aantal pijlen die ik nog mag zetten +1

      if player.spell != "" then { // als de spreuk niet gelijk is aan 'null'
        player.spell match { // checken welke spreuk het is
          case "beperkt" =>
            RhinoBeperkt = false //naar false zetten betekent dat we de spreuk terug krijgen
          case "dood" =>
            RhinoDood = false
          case "draai" =>
            RhinoDraai = false
        } }
    else if matrix(row)(column).isInstanceOf[Elephant] then
      numberOfElephants += 1
      if player.spell != "" then {
        player.spell match{
          case "beperkt" =>
            ElephantBeperkt = false
          case "dood" =>
            ElephantDood = false
          case "draai" =>
            ElephantDraai = false
        }
      }
    else if matrix(row)(column).isInstanceOf[Mountain] then // als het een berg is, einde
        if winner.isInstanceOf[Rhino] then
          println("Rhino Won !")
          System.exit(0)
        else
          println("Elephant Won !")
          System.exit(0)

  }

    // checken als het een leeg vak is
  def isFree(row: Int, column: Int): Boolean =
    if (matrix(row)(column) == Empty) then true else false

  // Een methode om de cellen te overlopen
  def forEach(f: (Int, Int, Element) => Unit): Unit =
    for i <- 0 until rows do for j <- 0 until columns do f(i, j, matrix(i)(j))

  // methode om alle cellen te bewegen wanneer er gepusht wrdt
  def moveAll(row: Int, column: Int, dir: String): Unit = {
    var count = 0

    dir match {
      case "up" =>
        var k = row
        while (k > 0 && matrix(k)(column) != Empty) { // zoeken hoeveel vakken je moet bewegen

            count += 1
            k -= 1
          }

        for (i <- row - count until row + 1) do { // bewegen van alle vakken
          pushUp(i, column)

        }
        flip()


      case "down" =>
        var k = row
        while (k < rows && matrix(k)(column) != Empty) { // zoeken aantal
          count += 1
          k += 1
        }
        for (i <- row + count - 1 until row - 1 by -1) do { // bewegen
          pushDown(i, column)
        }

        flip()

      case "left" =>
        var k = column
        while (k > 0 && matrix(row)(k) != Empty) { // zoeken aantal
          count += 1
          k -= 1
        }

        for (i <- column - count until column + 1) do { // bewegen
          pushLeft(row, i)
        }
        flip()

      case "right" =>
        var k = column
        while (k < columns - 1 && matrix(row)(k) != Empty) { // zoeken aantaal
          count += 1
          k += 1
        }

        for (i <- column + count until column - 1 by -1) do { // bewegen
          pushRight(row, i)
        }
        flip()

      case _ =>

    }
  }

  def checkForce(row: Int, column: Int): Int = {
    var element: Element = matrix(row)(column)
    var force = 0
  // checken hoeveel elementen er van dezelfde richting achter elkaar zitten
    element.dir match {
      case "up" =>
        var k = row
        while (k > 0 && matrix(k)(column).dir == "up") {
          force += 1
          k -= 1
        }

      case "down" =>
        var k = row
        while (k < rows && matrix(k)(column).dir == "down") {
          force += 1
          k += 1
        }

      case "left" =>
        var k = column
        while (k > 0 && matrix(row)(k).dir == "left") {
          force += 1
          k -= 1
        }

      case "right" =>
        var k = column
        while (k < columns && matrix(row)(k).dir == "right") {
          force += 1
          k += 1
        }

      case "mount" =>{}

      case "" =>  {}
    }
    return force
  }

  def checkAntiForce(row: Int, column: Int): Int = {
    var antiforce = 0
  // checken hvl elementen van de tegenovergestelde richting er zijn
    PlayerDirection match {

      case "up" =>
        var k = row
        while (k > 0 && matrix(k)(column) != Empty) {
          if matrix(k)(column).dir == "down" then {
            antiforce += 1 }
            k -= 1
        }

      case "down" =>
        var k = row
        while (k < rows && matrix(k)(column) != Empty) {
          if matrix(k)(column).dir == "up" then {
            antiforce += 1 }
            k += 1
        }

      case "left" =>
        var k = column
        while (k > 0 && matrix(row)(k) != Empty) {
          if matrix(row)(k).dir == "right" then {
          antiforce += 1}
          k -= 1
        }

      case "right" =>
        var k = column
        while (k < columns && matrix(row)(k) != Empty) {
          if matrix(row)(k).dir == "left" then {
            antiforce += 1
          }

          k += 1
        }
    }
    return antiforce
  }
// checken dat er niet meer dan 1 berg in de weg zit
  def checkMountains(row: Int, column: Int): Int = {
    var mountains = 0
    PlayerDirection match {
      case "up" =>
        var k = row
        while (k > 0 && matrix(k)(column) != Empty) {
          if matrix(k)(column).dir == "mount" then {
            mountains += 1
          }
          k -= 1
        }

      case "down" =>
        var k = row
        while (k > rows && matrix(k)(column) != Empty) {
          if matrix(k)(column).dir == "mount" then {
            mountains += 1
          }
          k += 1
        }

      case "left" =>
        var k = column
        while (k > 0 && matrix(row)(k) != Empty) {
          if matrix(row)(k).dir == "mount" then {
            mountains += 1
          }
          k -= 1
        }

      case "right" =>
        var k = column
        while (k < columns && matrix(row)(k) != Empty) {
          if matrix(row)(k).dir == "mount" then {
            mountains += 1
          }
          k += 1
        }

    }
    return mountains
  }

// push functies
  def pushUp(row: Int, column: Int): Unit = {
    if row - 1 < 0 then { // als laatste row
      winner = matrix(row + 1)(column)
      checkElement(row, column, matrix(row)(column))
      delete(row, column)
      flip()
    }
      // als er een vak vrij is
    else if isFree(row - 1, column) then
      matrix(row - 1)(column) = matrix(row)(column)
      delete(row, column)
      flip()

      // als volgend vak niet vrij is => kijken of je kan pushen met force
    else if !isFree(row - 1, column) then
      if checkForce(row, column) > checkAntiForce(row, column) &&
        matrix(row)(column).dir == "up" &&
        checkMountains(row, column) < 2
      then {
      moveAll(row, column, "up")
      flip()}
  }

  def pushDown(row: Int, column: Int): Unit = {
    // zelfde logica als pushUp
    if row + 1 > 4 then
      winner = matrix(row - 1)(column)
      checkElement(row, column, matrix(row)(column))
      delete(row, column)
      flip()

    else if isFree(row + 1, column) then
      matrix(row + 1)(column) = matrix(row)(column)
      delete(row, column)
      flip()

    else if !isFree(row + 1, column) then
      if checkForce(row, column) > checkAntiForce(row, column) &&
        matrix(row)(column).dir == "down" &&
        checkMountains(row, column) < 2
      then {
        moveAll(row, column, "down")
        flip()}
  }

  def pushRight(row: Int, column: Int): Unit = {
    // zelfde logica als pushUp
    if column == 4 then
      winner = matrix(row)(column - 1)
      checkElement(row, column, matrix(row)(column))
      delete(row, column)
      flip()

    else if isFree(row, column + 1) then
      matrix(row)(column + 1) = matrix(row)(column)
      delete(row, column)
      flip()

    else if !isFree(row, column + 1) then
      if checkForce(row, column) > checkAntiForce(row, column) &&
        matrix(row)(column).dir == "right" &&
        checkMountains(row, column) < 2
      then {
        moveAll(row, column, "right")
        flip()}

  }

  def pushLeft(row: Int, column: Int): Unit = {
    // zelfde logica als pushUp
    if column - 1 < 0 then {
      winner = matrix(row)(column + 1)
      checkElement(row, column, matrix(row)(column))
      delete(row, column)
      flip()
    }

    else if isFree(row, column - 1) then
      matrix(row)(column - 1) = matrix(row)(column)
      delete(row, column)
      flip()

    else if !isFree(row, column - 1) then
      if checkForce(row, column) > checkAntiForce(row, column) &&
        matrix(row)(column).dir == "left" &&
        checkMountains(row, column) < 2
      then {
        moveAll(row, column, "left")
        flip()}
  }

  def moveElement(row: Int, column: Int): Unit =
    // move elementen in de juiste gevraagde richting
    if matrix(row)(column).getClass == currentPlayer.getClass then
      PlayerDirection match {
// playerdirection is de richting gekozen door de player
        case "up" => pushUp(row, column)
        case "down" => pushDown(row, column)
        case "right" => pushRight(row, column)
        case "left" => pushLeft(row, column)

      }


  def add(row: Int, column: Int, player: Element): Unit =
    // toevoegen van onze pijlen

    // prints om te zien hoeveel er overblijven (gewoon checks voor mij)
    
    if isFree(row, column) && player.left > 0 then { // als vrije plek en genoeg pijlen
      matrix(row)(column) = player

        if player.isInstanceOf[Rhino] then { // aantal verminderen naargelang welk speler
          numberOfRhinos -= 1}
        else if player.isInstanceOf[Elephant] then {
          numberOfElephants -= 1 }

      flip() } // veranderen van player

    else if !isFree(row, column) && player.left > 0 then { // als er geen leeg plek is op eerste rij
      // dat je dan eerste element naar voor kan pushen van de eerste rij en er een andere achter zetten
      PlayerDirection match {
// checken welke rand er geplaatst wordt
        case "up" =>
          if checkAntiForce(row, column) == 0 && row == 4 then
          pushUp(row, column)

        case "down" =>
          if checkAntiForce(row, column) == 0 && row == 0 then
          pushDown(row, column)

        case "right" =>
          if checkAntiForce(row, column) == 0 && column == 0 then
          pushRight(row, column)

        case "left" =>
          if checkAntiForce(row, column) == 0 && column == 4 then
          pushLeft(row, column)
      }
      if matrix(row)(column) == Empty then
        matrix(row)(column) = player

    }

// element roteren
  def rotateElement(Dir: String, element: Element): Unit =
    if element.getClass == currentPlayer.getClass then
      element.rotate(Dir)
      flip()

  // checken welke spreuk speler heeft gekozen
  def checkSpell(row: Int, column: Int): Unit = {
    if row > 4 && column == 4 then
      spellButton = (column, row) match {
        case (4, 5) => "beperkt"
        case (4, 6) => "dood"
        case (4, 7) => "draai"
        case (_, _) => ""
      }
  }

// richting checken die gekozen werd door speler
  def checkDirections(row: Int, column: Int): Unit =
    if row > 4 && column < 5 then
      PlayerDirection = (column, row) match {
        case (3, 6) => "right"
        case (2, 7) => "down"
        case (2, 5) => "up"
        case (1, 6) => "left"
        case (_, _) => ""
      }
      currentPlayer.dir = PlayerDirection

// check Button die gekozen werd voor beweging
  def checkButton(row: Int, column: Int): Unit =
    if column > 4 then
      GameButton = (column, row) match {
        case (5, 0) => "rotate"
        case (5, 1) => "move"
        case (5, 2) => "add"
        case (_, _) => GameButton
      }

  // spreuk toevoegen aan een element
  def addSpell(player: Element, spell: String): Unit = {
    player.spell = spell
    if player.isInstanceOf[Rhino] then { // dan variabele weghalen zodat je deze niet twee keer kan gebruiken
      spell match {
        case "beperkt" =>
          RhinoBeperkt = true
        case "dood" =>
          RhinoDood = true
        case "draai" =>
          RhinoDraai = true
      }
    }
    else if player.isInstanceOf[Elephant] then {
      spell match {
        case "beperkt" =>
          ElephantBeperkt = true
        case "dood" =>
          ElephantDood = true
        case "draai" =>
          ElephantDraai = true
      }
    }
    flip()
  }

  // bij elke zet => grid update met spreuken
  def updateSpell(): Unit = {
    for i <- 0 until rows do
      for j <- 0 until columns do
        matrix(i)(j).applySpell()
          if matrix(i)(j).iskilled then
            delete(i, j)
  }

  def flip(): Unit = // veranderen van zet en speler
    if (currentPlayer.isInstanceOf[Rhino]) then
      currentPlayer = Elephant(PlayerDirection)
    else {
      currentPlayer = Rhino(PlayerDirection)
    }
    GameButton = ""
    PlayerDirection = ""
    spellButton = ""
    playerdrawing.flip()
    updateSpell()

  // procedure die checkt welke combinatie van knoppen er zijn en wat er gedaan moet worden

  def checkMove(row: Int, column: Int): Unit =
    if column < 5 && row < 5 then
      var el: Element = matrix(row)(column)
      if GameButton == "add" && PlayerDirection != "" then
        if row < 1 || row > rows - 2 || column < 1 || column > columns - 2 then
          add(row, column, currentPlayer)

      // bewegen als de richting niet null is
      if GameButton == "move" && PlayerDirection != "" then

        if !el.isblocked then
          el.turns -= 1
          moveElement(row, column)

        // roteren als de richting niet null is
      if GameButton == "rotate" && PlayerDirection != "" then

        rotateElement(PlayerDirection, el)

        //spreuk toevoegen als er een spreuk gekozen is
      if GameButton == "add" && spellButton != "" then
// deze check wordt gedaan zodat je niet op jezelf een spreuk oproept
        if !currentPlayer.isInstanceOf[Rhino] then
          spellButton match {
            case "beperkt" =>
              if !RhinoBeperkt then
                addSpell(el, spellButton)
            case "dood" =>
              if !RhinoDood then
                addSpell(el, spellButton)
            case "draai" =>
              if !RhinoDraai then
                addSpell(el, spellButton)
          }
        else if !currentPlayer.isInstanceOf[Elephant] then
          spellButton match {
            case "beperkt" =>
              if !ElephantBeperkt then
                addSpell(el, spellButton)
            case "dood" =>
              if !ElephantDood then
                addSpell(el, spellButton)
            case "draai" =>
              if !ElephantDraai then
                addSpell(el, spellButton)
          }