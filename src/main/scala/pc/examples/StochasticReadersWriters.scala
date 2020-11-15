package pc.examples

import java.util.Random

import pc.modelling.SPN.toCTMC
import pc.modelling.{CTMCSimulation, SPN}
import pc.utils.MSet

object StochasticReadersWriters extends App {

  object place extends Enumeration {
    val IDLE, CHOOSE, READEREQ, WRITEREQ, LOCK, READING, WRITING = Value
  }

  type State = place.Value
  import place._

  val stocReadersWriters = SPN[State](
    (MSet(IDLE), _=> 1.0, MSet(CHOOSE), MSet()),
    //readers
    (MSet(CHOOSE), _ => 200000.0, MSet(READEREQ), MSet()),
    (MSet(READEREQ, LOCK), _=>100000.0, MSet(READING, LOCK), MSet()),
    (MSet(READING), _=>0.1, MSet(IDLE), MSet()), //TODO vedi rate p6
    //writers
    (MSet(CHOOSE), _=> 100000.0, MSet(WRITEREQ), MSet()),
    (MSet(WRITEREQ, LOCK), _=>100000.0, MSet(WRITING), MSet(READING)),
    (MSet(WRITING), _=>0.2, MSet(LOCK, IDLE), MSet())
  )

  val rwAnalysis = CTMCSimulation(toCTMC(stocReadersWriters))

  println(rwAnalysis.newSimulationTrace(MSet(READING, IDLE, LOCK),new Random)
    .take(20)
    .toList.mkString("\n"))

  //println(MSet(READING, READING).asMap.get(READING).getOrElse(10))
}
