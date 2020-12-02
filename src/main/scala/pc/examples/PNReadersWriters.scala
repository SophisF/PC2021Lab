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
 // println(readersWritersSystem().paths(MSet(IDLE,LOCK),10).toList.mkString("\n"))

  //small API
  def verifyAPI(mSet: List[MSet[Place]]): Boolean ={
    //use other method
    safetyCheck(10)(mSet)
  }

  def safetyCheck(depth: Int)(condition: List[MSet[Place]]): Boolean ={
    val list = readersWritersSystem().paths(MSet(IDLE, IDLE, LOCK),depth).toList
    println(list)
    list.forall(m => m.forall(e => !condition.exists(c => e.matches(c))))
  }

  //safetyCheck(10)(verifyAPI(List(MSet(READING, WRITING),MSet(WRITING, WRITING))))
  /*val list = readersWritersSystem().paths(MSet(IDLE, IDLE, LOCK),5).toList
  println(list)
  val prova = list.forall(m => m.forall(e => !(e.matches(MSet(WRITING, WRITING)) || e.matches(MSet(WRITING, READING)))))
  println(prova)
  */
  print(verifyAPI(List(MSet(IDLE, IDLE), MSet(WRITING, IDLE))))
}
