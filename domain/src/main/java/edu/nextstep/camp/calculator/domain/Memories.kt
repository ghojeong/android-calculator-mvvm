package edu.nextstep.camp.calculator.domain

data class Memories(
    private val memories: List<Memory>
) : List<Memory> by memories {
    operator fun plus(memory: Memory): Memories = Memories(memories + memory)
}
