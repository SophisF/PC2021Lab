package pc.examples

import pc.modelling.CTMC

object StochasticReadersWriters {

  object place extends Enumeration {
    val IDLE, CHOOSE, READREQ, WRITEREQ, LOCK, READING, WRITING = Value
  }

  type State = place.Value
  import place._

  val stocReadersWriters: CTMC[State] = CTMC.ofTransitions(
    (IDLE,1.0, CHOOSE),
    //readers
    (CHOOSE, 200000.0, READREQ),
    ((READREQ, LOCK), 100000.0, (READING, LOCK)),
    (READING, 0.1*READING.id, IDLE),
    //writers
    (CHOOSE, 100000.0, WRITEREQ),
    ((WRITEREQ, LOCK), 100000.0, WRITING),// READING),
    (WRITING, 0.2, (LOCK, IDLE))
  )

  place.values.foreach(p => println(p, stocReadersWriters.transitions(p)))
}
