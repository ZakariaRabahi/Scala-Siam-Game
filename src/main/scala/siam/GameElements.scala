package siam

// olifant representatie
class Elephant(direction: String) extends Element {
  var dir = direction
  var left = numberOfElephants

  
  override def rotate(NewDir: String): Unit =
    dir = NewDir
}

// rhino representatie
class Rhino(direction: String) extends Element{
  var dir = direction
  var left = numberOfRhinos


  override def rotate(NewDir: String): Unit =
    dir = NewDir
}

// mountain representatie
class Mountain(direction: String) extends Element{
  var dir = direction
  var left = numberOfMountains


}
