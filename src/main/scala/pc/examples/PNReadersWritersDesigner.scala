package pc.examples

import pc.examples.PNReadersWriters.Place
import pc.examples.PNReadersWriters.place._
import pc.modelling.PetriNet
import pc.modelling.PetriNet._
import pc.utils.MSet

object PNReadersWritersDesigner extends App {

    // DSL-like specification of A Petri Net
  def readersWritersSystem() = toSystem(PetriNet[Place](
    MSet(IDLE) ~~> MSet(CHOOSE),
    //readers
    MSet(CHOOSE) ~~> MSet(READREQ),
    MSet(READREQ, LOCK) ~~> MSet(READING, LOCK),
    MSet(READING) ~~> MSet(IDLE),
    //writers
    MSet(CHOOSE) ~~> MSet(WRITEREQ) ^^^ MSet(READREQ), //> priority at process that wants to read
    MSet(WRITEREQ, LOCK) ~~> MSet(WRITING) ^^^ MSet(READING),
    MSet(WRITING) ~~> MSet(LOCK, IDLE)
  ))

  var list = readersWritersSystem().paths(MSet(IDLE, IDLE, LOCK),50).toList
  // example usage
  println(list.mkString("\n"))

}
