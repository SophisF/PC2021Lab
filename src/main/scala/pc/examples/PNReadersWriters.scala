package pc.examples

import pc.modelling.PetriNet
import pc.modelling.PetriNet._
import pc.utils.MSet

object PNReadersWriters extends App {

  object place extends Enumeration {
    val A, B, C, D,E,F,G = Value
  }
  type Place = place.Value
  import place._

  // DSL-like specification of A Petri Net
  def readersWritersSystem() = toSystem(PetriNet[Place](
    MSet(A) ~~> MSet(B),
    //readers
    MSet(B) ~~> MSet(C),
    MSet(C, E) ~~> MSet(F, E),
    MSet(F) ~~> MSet(A),
    //writers
    MSet(B) ~~> MSet(D),
    MSet(D, E) ~~> MSet(G) ^^^ MSet(F),
    MSet(G) ~~> MSet(E, A)
  ))

  // example usage
  println(readersWritersSystem().paths(MSet(A,E),10).toList.mkString("\n"))
}
