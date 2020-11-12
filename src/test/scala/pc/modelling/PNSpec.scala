package pc.modelling

import org.scalatest.FlatSpec

class PNSpec extends FlatSpec{

  import pc.examples.PNMutualExclusion, pc.examples.PNMutualExclusion.place._
  import pc.utils.MSet
  import pc.examples.PNReadersWriters

  val pnME = PNMutualExclusion.mutualExclusionSystem()
  val pnRW = PNReadersWriters.readersWritersSystem()

  "PN for mutual exclusion" should "properly generate 7-length paths" in {
    val expected1 = List(MSet(N,N), MSet(T,N), MSet(T,T), MSet(C,T), MSet(T), MSet(C), MSet())
    val expected2 = List(MSet(N,N), MSet(T,N), MSet(C,N), MSet(C,T), MSet(T), MSet(C), MSet())
    val expected3 = List(MSet(N,N), MSet(T,N), MSet(C,N), MSet(N), MSet(T), MSet(C), MSet())

    assert(pnME.paths(MSet(N,N),7).toSet == Set(expected1,expected2,expected3))
  }

  import pc.examples.PNReadersWriters.place._

  "ReadersWriters PN for mutual exclusion" should "not fail" in {
    val expected = List(MSet(READING,WRITING), MSet(WRITING,WRITING), MSet(WRITING,LOCK),
        MSet(LOCK,WRITING), MSet(WRITING,READING))
    assert(!pnRW.paths(MSet(IDLE, LOCK),100).toSet.contains(expected))
  }
}
