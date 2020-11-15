package pc.examples

import pc.examples.PNReadersWriters.Place
import pc.modelling.PetriNet._
import pc.utils.MSet
import pc.examples.PNReadersWriters.place._
import pc.modelling.PetriNet
object PNReadersWritersNoStarvation extends App {

    // DSL-like specification of A Petri Net
  def readersWritersSystem() = toSystem(PetriNet[Place](
    MSet(IDLE) ~~> MSet(CHOOSE),
    //readers
    MSet(CHOOSE) ~~> MSet(READREQ) ^^^ MSet(WRITEREQ),
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
