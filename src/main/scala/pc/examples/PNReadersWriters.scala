package pc.examples

import pc.modelling.PetriNet
import pc.modelling.PetriNet._
import pc.utils.MSet

object PNReadersWriters extends App {

  object place extends Enumeration {
    val IDLE, CHOOSE, READREQ, WRITEREQ, LOCK, READING, WRITING = Value
  }
  type Place = place.Value
  import place._

  // DSL-like specification of A Petri Net
  def readersWritersSystem() = toSystem(PetriNet[Place](
    MSet(IDLE) ~~> MSet(CHOOSE),
    //readers
    MSet(CHOOSE) ~~> MSet(READREQ),
    MSet(READREQ, LOCK) ~~> MSet(READING, LOCK),
    MSet(READING) ~~> MSet(IDLE),
    //writers
    MSet(CHOOSE) ~~> MSet(WRITEREQ),
    MSet(WRITEREQ, LOCK) ~~> MSet(WRITING) ^^^ MSet(READING),
    MSet(WRITING) ~~> MSet(LOCK, IDLE)
  ))

  // example usage
  println(readersWritersSystem().paths(MSet(IDLE,LOCK),10).toList.mkString("\n"))
}
