package siam

// abstracte class voor alle spel elementen
abstract class Element {
  
  var dir: String // richting bijhouden
  var left: Int // hoeveel er overbljven
  var spell: String = "" // spreuk die uitgevoerd wordt op element


  var turns: Int = 3 // aantal zets over
  var isblocked: Boolean = false // false wanneer spreuk niet op geoefend wordt (beperkte beweging)

  var iskilled: Boolean = false // false wanneer spreuk niet op geoefend wordt (onverwacht dood)

  // procedure voor spreuk rotatie
  def rotate(NewDir: String): Unit =
    dir = NewDir


// spreuk toepassen
  def applySpell(): Unit = {
    spell match {
      
      // als ik een extra spreuk wil toevoegen, zet ik deze hier met de nodige code
      
      case "draai" =>
        dir = dir match
          case "up" => "right"
          case "right" => "down"
          case "down" => "left"
          case "left" => "up"

      case "dood" =>
        if turns < 0 then
          iskilled = true
        else
          turns -= 1


      case "beperkt" =>
        if turns == 0 then
          isblocked = true

      case "" =>

    }

  }
}
      

